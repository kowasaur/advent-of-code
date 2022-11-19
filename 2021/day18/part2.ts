import assert from "assert";
import { readFileSync } from "fs";
import { inspect } from "util";

type NestedNumArray = number | NestedNumArray[];
type ChildIndex = 0 | 1;
type Child = [Node, ChildIndex];

class Node {
    public parent: Node | null;
    public children: (number | Node)[];
    public index: ChildIndex | null; // index in parent's children

    constructor(
        arr: NestedNumArray[] | [Node, Node],
        parent: Node | null = null,
        index: ChildIndex | null = null
    ) {
        this.parent = parent;
        this.index = index;

        if (arr[0].constructor.name == "Node") {
            this.children = arr as [Node, Node];
            return;
        }

        const c1 = Number.isInteger(arr[0])
            ? (arr[0] as number)
            : new Node(arr[0] as NestedNumArray[], this, 0);
        const c2 = Number.isInteger(arr[1])
            ? (arr[1] as number)
            : new Node(arr[1] as NestedNumArray[], this, 1);
        this.children = [c1, c2];
    }

    public add(other: Node) {
        const new_root = new Node([this, other]);
        this.parent = new_root;
        this.index = 0;
        other.parent = new_root;
        other.index = 1;
        new_root.reduce();
        return new_root;
    }

    public reduce() {
        while (this.explode() || this.split()) {}
    }

    public sibling(): [Node | number, ChildIndex] {
        assert(this.parent !== null && this.index !== null, "There is no sibling of root");
        const index = Math.abs(this.index - 1) as ChildIndex;
        return [this.parent.children[index], index];
    }

    public leftmostNum(condition: (n: number) => boolean = _ => true): Child | null {
        const c1 = this.children[0];
        const c2 = this.children[1];

        const c1_num = Number.isInteger(c1);
        if (c1_num && condition(c1 as number)) return [this, 0];
        if (!c1_num) {
            const n1 = (c1 as Node).leftmostNum(condition);
            if (n1) return n1;
        }

        const c2_num = Number.isInteger(c2);
        if (c2_num && condition(c2 as number)) return [this, 1];
        if (!c2_num) {
            const n2 = (c2 as Node).leftmostNum(condition);
            if (n2) return n2;
        }

        return null;
    }

    // copied leftmostNum and just switched things and removed the condition to save time
    public rightmostNum(): Child | null {
        const c1 = this.children[0];
        const c2 = this.children[1];

        if (Number.isInteger(c2)) return [this, 1];
        const n2 = (c2 as Node).rightmostNum();
        if (n2) return n2;

        if (Number.isInteger(c1)) return [this, 0];
        const n1 = (c1 as Node).rightmostNum();
        if (n1) return n1;

        return null;
    }

    public neighbour(left: boolean): Child | null {
        if (this.parent === null) return null;

        if (this.index === Number(left)) {
            const [node, sib_index] = this.sibling();
            if (Number.isInteger(node)) return [this.parent, sib_index];
            if (left) return (node as Node).rightmostNum();
            return (node as Node).leftmostNum();
        }

        return this.parent.neighbour(left);
    }

    public explode(nest_level = 0) {
        if (nest_level == 4) {
            assert(this.parent !== null && this.index !== null, "nest_level calculated wrong");
            const left = this.neighbour(true);
            if (left) (left[0].children[left[1]] as number) += this.children[0] as number;
            const right = this.neighbour(false);
            if (right) (right[0].children[right[1]] as number) += this.children[1] as number;

            this.parent.children[this.index] = 0;
            return true;
        }

        for (const child of this.children) {
            if (!Number.isInteger(child) && (child as Node).explode(nest_level + 1)) return true;
        }

        return false;
    }

    public split() {
        const over_10 = this.leftmostNum(n => n >= 10);

        if (over_10) {
            const [parent, index] = over_10;
            const n = parent.children[index] as number;
            const split_node = new Node([Math.floor(n / 2), Math.ceil(n / 2)], parent, index);
            parent.children[index] = split_node;
            return true;
        }

        return false;
    }

    public magnitude(): number {
        const [c1, c2] = this.children;
        const m1 = Number.isInteger(c1) ? (c1 as number) : (c1 as Node).magnitude();
        const m2 = Number.isInteger(c2) ? (c2 as number) : (c2 as Node).magnitude();
        return m1 * 3 + m2 * 2;
    }

    // For debugging
    public print(do_print = true): NestedNumArray {
        const c1 = this.children[0];
        const c2 = this.children[1];

        const arr1 = Number.isInteger(c1) ? (c1 as number) : (c1 as Node).print(false);
        const arr2 = Number.isInteger(c2) ? (c2 as number) : (c2 as Node).print(false);

        const final = [arr1, arr2];

        if (do_print) console.log(inspect(final, false, null, true));

        return final;
    }
}

const file = readFileSync("input.txt", "utf8").split("\n");

const magnitudes: number[] = [];
file.forEach((l1, i1) =>
    file.forEach((s2, i2) => {
        if (i1 !== i2) magnitudes.push(new Node(eval(l1)).add(new Node(eval(s2))).magnitude());
    })
);
const max = Math.max(...magnitudes);

console.log(max);

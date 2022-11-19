with open("input.txt") as f:
    image_enhancement, image_str = f.read().split("\n\n")

input_image = { "maxX": -100, "maxY": -100, "minX": 100, "minY": 100, "infinite": "."}

def change_pixel(image, pixel, symbol):
    x, y = pixel
    image[(x, y)] = symbol
    if x > image["maxX"]:
        image["maxX"] = x
    elif x < image["minX"]:
        image["minX"] = x
    if y > image["maxY"]:
        image["maxY"] = y
    elif y < image["minY"]:
        image["minY"] = y

for y, row in enumerate(image_str.splitlines()):
    for x, pixel in enumerate(row):
        change_pixel(input_image, (x,y), pixel)

def enhance_pixel(pixel: tuple[int, int], image: dict):
    index = 0
    ox, oy = pixel
    for y in range(-1, 2):
        for x in range(-1, 2):
            index <<= 1
            if image.get((x + ox, y + oy), image["infinite"]) == "#":
                index += 1
    return image_enhancement[index]

EXTRA = 1

def enhance_image(image: dict):
    new_image = { "maxX": -100, "maxY": -100, "minX": 100, "minY": 100}
    for y in range(image["minY"] - EXTRA, image["maxY"] + EXTRA + 1):
        for x in range(image["minX"] - EXTRA, image["maxX"] + EXTRA + 1):
            change_pixel(new_image, (x,y), enhance_pixel((x,y), image))
    if image_enhancement[0] == "#" and image["infinite"] == ".":
        new_image["infinite"] = "#"
    else:
        new_image["infinite"] = "."
    return new_image

# For debugging (and it looks cool)
def print_image(image: dict):
    for y in range(image["minY"], image["maxY"] + 1):
        s = ""
        for x in range(image["minX"], image["maxX"] + 1):
            s += image[x,y]
        print(s)

enhanced = input_image
for i in range(0, 50):
    enhanced = enhance_image(enhanced)

lit = list(enhanced.values()).count("#")

print(lit)

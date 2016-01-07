__author__ = 'Leon Agmon Nacht'

import cv2
import numpy as np
import IPCamera

path = 'ftp://roborio-3316.local/home/lvuser/Pics/image_from_camarea.jpeg'
#path = 'http://192.168.8.161/snap.jpg?'
flag_path = 'temp.txt'
result_file = 'result.txt'
min_color = np.array([110,50,50]) # Blue.
max_color = np.array([130,255,255]) # Blue.
minimum_bounding_size = 500
cam = IPCamera.IPCamera(path, flag_path, result_file, minimum_bounding_size , min_color, max_color)

with open(cam.flag_file_path, "w") as f:
    f.write("1")

#distance, width, image = 10, 10, cv2.imread(path) # Read docs to implement, won't work without it!
#cam.set_distance_vars(distance, width, image)
cam.known_distance = 100
cam.known_width = 11
cam.focal_length = 100**2 / 11

while True:
    cam.update_image()
    cam.update_object_scales()
    distance_from_camera = cam.calculate_distance()
    if cam.masked_image is not None:
        cv2.imshow('MASKED IMG', cam.masked_image)
        cv2.imshow('ORIGINAL IMG', cam.current_image)
    print(distance_from_camera)
    k = cv2.waitKey(5) & 0xFF
    if k == 27:
        break

cv2.destroyAllWindows()

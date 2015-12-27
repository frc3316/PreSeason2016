__author__ = 'Leon Agmon Nacht'

import cv2
import numpy as np
import IPCamera

path = 'ftp://roborio-3316.local/home/lvuser/Pics/image_from_camarea.jpeg'
min_color = np.array([110,50,50]) # Blue.
max_color = np.array([130,255,255]) # Blue.
minimum_bounding_size = 500
cam = IPCamera.IPCamera(path, minimum_bounding_size, min_color, max_color)

distance, width, image = None # Read docs to implement, won't work without it!
cam.set_distance_vars(distance, width, image)

while True:
    cam.update_image()
    cam.update_object_scales()
    distance_from_camera = cam.calculate_distance()
    print(distance_from_camera)

    k = cv2.waitKey(5) & 0xFF
    if k == 27:
        break

cv2.destroyAllWindows()

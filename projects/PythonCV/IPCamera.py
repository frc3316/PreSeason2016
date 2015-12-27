__author__ = 'Leon Agmon Nacht'

import cv2
import numpy as np
import urllib


class IPCamera():
    '''
    IPCamera- This class handles all the needed variables and methods for the camera (python side) of the project.
    '''

    def __init__(self, path, minimum_bounding_size, min_color, max_color):
        '''
        This method initialising the variables for the IPCamera instance.
        :param path: The FTP path for the current jpeg image (the jpeg image should be updated using the roborio code).
        :param minimum_bounding_size: The minimum contour area to be considered as an object.
        :param max_color: The max color to be passed in the mask. (in the format: np.array([R, G, B])
        :param min_color: The min color to be passed in the mask. (in the format: np.array([R, G, B])
        :return: None
        '''
        self.path = path
        self.current_image = None
        self.current_image_object_width = None
        self.known_distance = None
        self.known_width = None
        self.focal_length = None
        self.distance_image = None
        self.minimum_bounding_rect_size = minimum_bounding_size
        self.max_color = max_color
        self.min_color = min_color
        self.image_width = None
        self.image_height = None
        # The scales of the object in self.current_image, should be updated using self.update_object_scales.
        self.object_width = None
        self.object_height = None
        self.object_x = None
        self.object_y = None

    # MARK: Running Once Methods.

    def set_distance_vars(self, distance, width, image):
        '''
        This method sets the needed variables for calculating the distance of an object from the camera.

        :param distance: The distance of the object from the camera in image.
        :param width: The real world width of the object.
        :param image: The pre-taken image of the object, in image the object is in distance distance(var) from the camera.
        :return: None
        '''
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        gray = cv2.GaussianBlur(gray, (5, 5), 0)
        edged = cv2.Canny(gray, 35, 125)

        # find the contours in the edged image and keep the largest one
        # we'll assume that this is our wanted object in the image
        (cnts, _) = cv2.findContours(edged.copy(), cv2.RETR_LIST, cv2.CHAIN_APPROX_SIMPLE)
        marker = max(cnts, key = cv2.contourArea)

        self.distance_image = image
        self.known_distance = distance
        self.known_width = width

        self.focalLength = (marker[1][0] * distance) / width


    def set_image_scales(self):
        '''
        This method sets the scales of the image(self.current_image).
        This method is auto called, and should be called only if the image size changes.
        :return:
        '''
        height, width = self.current_image.shape[:2]
        self.image_height = height
        self.image_width
    # MARK: Update Methods.

    def update_image(self):

        '''
        This method is called when needed to update the self.current_image.
        self.current_image is being updated to the image in self.path.
        :return: None.
        '''

        if self.current_image is not None and self.image_width is None and self.image_height is None:
            self.set_image_scales()

        req = urllib.urlopen(self.path)
        arr = np.asarray(bytearray(req.read()), dtype=np.uint8)
        img = cv2.imdecode(arr, -1)
        self.current_image = img  # The current image in the instance path. (already in cv2 format).

    def update_object_scales(self):
        '''
        This method updates the vars holding the scales of the object.
        :return: None
        '''
        (x, y, w, h) = self.calculate_bounding_rect()
        self.object_height = h
        self.object_width = w
        self.object__x = x
        self.object_y = y

    # MARK: Calculating Methods.

    def calculate_bounding_rect(self):
        '''
        This method calculates the bounding rect of the biggest right color object in self.current_image.
        :return: None if no object else the bounding rect in the format of (x, y, w, h).
        '''
        hsv = cv2.cvtColor(self.current_image, cv2.COLOR_BGR2HSV)

        # Threshold the HSV image to get only blue colors.
        mask = cv2.inRange(hsv, self.min_color, self.max_color)

        # Bitwise-AND mask and original image.
        res = cv2.bitwise_and(self.current_image, self.current_image, mask= mask)

        thresh = cv2.threshold(mask, 25, 255, cv2.THRESH_BINARY)[1]

        # dilate the thresholded image to fill in holes, then find contours on thresholded image.
        thresh = cv2.dilate(thresh, None, iterations=2)
        (cnts, _) = cv2.findContours(thresh.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
        if cnts != []:
            c = max(cnts, key = cv2.contourArea)
            if cv2.contourArea(c) > self.minimum_bounding_rect_size:
                return cv2.boundingRect(c)
        return None

    def calculate_distance(self):
        '''
        This method calculates the distance of the object from the camera.
        Using- distance from camera = (self.known_width * self.focal_length) / self.image_object_width.
        Should call self.update_object_scales before.
        :return: The distance of the object from the camera.
        '''
        return (self.known_width * self.focal_ength) / self.object_width


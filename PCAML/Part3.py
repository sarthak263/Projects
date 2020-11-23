#! /usr/bin/python3
from PCAFunc import *
import cv2
import glob
import os
from PIL import Image


def main():
    standM1 = P2StandMatrix()
    mean = standM1.mean(axis=0)
    std = np.std(standM1, axis=0, dtype=np.float64)
    standM1 = (standM1- mean) / std
    size =40,40
    myImage = Image.open('yalefaces/subject02.centerlight.png')

    myImage = myImage.resize(size, Image.ANTIALIAS)
    matrix = np.array(myImage)
    flatImage = matrix.flatten()
    matrix  = np.asarray(flatImage)


    standM2 = get_standardMatrix(matrix)
    '1*1600 M2'
    standM2 = np.array([np.asarray(standM2)])
    eigenVal,eigenVec = get_eigens(standM1)

    for i in range(0,1600):
        matrix = np.dot(standM2,eigenVec[:, :i+1])
        matrix = np.dot(matrix, eigenVec[0:i+1])
        matrix *= std
        matrix += mean
        pic = np.reshape(matrix,size)
        rescale = (pic-np.min(pic))/(np.max(pic)-np.min(pic))
        rescale = rescale*255
        pic = Image.fromarray(rescale.astype('uint8'))
        pic.save("pic"+str(i)+".jpg")
        pic.close()

    listPic = glob.glob('*.jpg')

    video = cv2.VideoWriter('Hw1.avi',0,30,size)

    for image in listPic:
        video.write(cv2.imread(image))
        os.remove(image)

    cv2.destroyAllWindows()
    video.release()

main()
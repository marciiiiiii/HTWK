import pygame
from Player import Player

class Controller():
    def __init__(self, width, height):
        self.__sWidth = width
        self.__sHeight = height
        self.__player = Player(width/2, height*5/4, 20, 20 )
     
    def getPlayer(self):
        return self.__player
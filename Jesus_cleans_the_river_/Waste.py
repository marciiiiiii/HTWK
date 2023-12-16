from Item import Item
import pygame

class Waste(Item):
     def __init__(self, posX, posY, imagePath, scoreChange, trashmeterChange, width, height):
        super().__init__(posX, posY, imagePath, scoreChange, trashmeterChange, width, height)
from Item import Item
import pygame

class Fish(Item):
    def __init__(self, posX, posY, scoreChange, trashmeterChange, width, height):
        super().__init__(posX, posY, scoreChange, trashmeterChange, width, height)
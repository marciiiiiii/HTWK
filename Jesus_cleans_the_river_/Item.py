from abc import ABC, abstractmethod
import pygame

class Item(ABC, pygame.sprite.Sprite):
    def __init__(self, posX, posY, scoreChange, trashmeterChange, width, height):
        super().__init__()
        # self.image = pygame.image.load(imagePath).convert_alpha() INSERT IMAGEPATH-PARAM to each subclass
        self._image = pygame.Surface((width,height)) # just for testing purposis
        self._rect = self._image.get_rect(midtop = (posX,posY))
        self._image.fill("Red") # just for testing purposis
        self._scoreChange = scoreChange
        self._trashmeterChange = trashmeterChange
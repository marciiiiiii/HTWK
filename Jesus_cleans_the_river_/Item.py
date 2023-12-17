import pygame
from random import randint, choice
import math


# @brief base class of 4 spawnable items
# @detail   -items of 4 types may be spawned in the game to be collected or bypassed by player
#           -sub classes are: Fish, Waste, HazWaste and Bible
#           -handling is done in Game.py
class Item(pygame.sprite.Sprite):

    # @brief initiator of base class
    # @detail   -__width and __height are unscaled dimensions of game window, used to fit objects at right location
    #            in game
    #           -__scale is the scaling factor to adjust window dimensions to acctualy window size
    #           -image will be first imported by imagePath, then scaled
    #           -score- and trashmeterChanger are selected at initiation-time to set item values and what they do to
    #            score and trashmeter
    # @param     screenWidth
    # @param     screenHeight
    # @param     scoreChange
    # @param     trashmeterChange
    # @param     scale
    # @param     imagePath
    def __init__(self, screenWidth, screenHeight, scoreChange, trashmeterChange, scale, imagePath, game):
        super().__init__()
        self.__width = screenWidth
        self.__height = screenHeight
        self.__scale = scale
        self.__game = game
        self.image = pygame.image.load(imagePath).convert_alpha()
        self.image = pygame.transform.scale(self.image, (self.scaleTimesImgage(True), self.scaleTimesImgage(False)))
        self.rect = self.image.get_rect(midbottom=(self.startPosition(), 0))
        self.__scoreChange = scoreChange
        self.__trashmeterChange = trashmeterChange

    # @brief returns random spawning x-coord. for items in bounds of river
    # @return x-coordinate
    def startPosition(self):
        return randint(math.floor(31 * self.__scale + self.image.get_width() / 2),
                       math.ceil(self.__width - 31 * self.__scale - self.image.get_width() / 2))

    # @brief returns scaled image width or height
    # @param width: True for scaled return of width, False for scaled return of height
    # @return image size * scaling factor
    def scaleTimesImgage(self, width):
        if width:
            return self.image.get_width() * self.__scale
        else:
            return self.image.get_height() * self.__scale

    # @brief speed of objects moving
    def movement(self):
        self.rect.y += 4

    # @brief destroys imtems after 100 pixels out of bound of screen and prints information
    def destroy(self):
        if self.rect.y >= self.__height + 100:
            self.__game.getWasteMeter().changeValue(-1 * self.getScoreChange())
            self.kill()
            del self
            #print("Object " + str(type(self)) + "destroyed at " + str(self.rect.y)) For debug only

    # @ combines all game loop functions for usage in game loop
    def update(self):
        self.movement()
        self.destroy()

    def getScoreChange(self):
        return self.__scoreChange

    def getTrashmeterChange(self):
        return self.__trashmeterChange

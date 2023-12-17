import math

import pygame


class WasteMeter(pygame.sprite.Sprite):
    START_WASTE_VAL = 50
    MAX_WASTE_VAL = 100
    MIN_WASTE_VAL = 0
    IMG_WIDTH = 23
    IMG_HEIGHT = 13

    def __init__(self, gui):
        super().__init__()
        self.__waste = self.START_WASTE_VAL
        self.__images = {}
        self.__gui = gui
        self.loadImages()

        self.image = self.__images.get(self.calcSpeedoPos())
        self.rect = self.image.get_rect(midtop=((gui.getScale() * 31) / 2, 20))

        self.__group = pygame.sprite.GroupSingle()
        self.__group.add(self)

    def reset(self):
        self.__waste = self.START_WASTE_VAL

    def changeValue(self, value):
        self.__waste += value
        if self.__waste >= self.MAX_WASTE_VAL:
            self.__waste = self.MAX_WASTE_VAL
        if self.__waste <= self.MIN_WASTE_VAL:
            self.__waste = self.MIN_WASTE_VAL
        print("WASTEMETER: " + str(self.__waste))

    def loadImages(self):
        scaled_width = self.__gui.getScale() * self.IMG_WIDTH
        scaled_height = self.__gui.getScale() * self.IMG_HEIGHT
        for i in range(1, 14):
            self.__images[i] = pygame.transform.scale(pygame.image.load('images/TOM' + str(i) + '.png').convert_alpha(), (scaled_width, scaled_height))

    def update(self, screen):
        self.image = self.__images.get(self.calcSpeedoPos())
        self.__group.draw(screen)

    def wasteGameLoose(self):
        if self.__waste <= self.MIN_WASTE_VAL:
            return True
        else:
            return False

    def calcSpeedoPos(self):
        share = (self.MAX_WASTE_VAL - self.MIN_WASTE_VAL) / self.__images.__len__()
        pos = math.floor(self.__waste / share)
        if pos < 1:
            pos = 1
        if pos > self.__images.__len__() + 1:
            pos = self.__images.__len__() + 1
        return round(pos)

import pygame
from Game import Game


class Menu:

    def __init__(self, gui):
        self.__click = False
        self.__gui = gui
        self.__gui.generateScreen()

    def menuLoop(self):
        while True:
            self.__gui.fillScreen()
            self.__gui.drawText('main menu', pygame.font.SysFont(None, 20),(255, 255, 255), 20, 20)

            mouseX, mouseY = pygame.mouse.get_pos()

            #generates and renders Button
            buttonStartGame = self.__gui.generateImage(50, 100, 200, 50)
            self.__gui.render((255, 0, 0), buttonStartGame)

            #if mouse collides Button and click = True new Game will be created and started
            if buttonStartGame.collidepoint((mouseX, mouseY)):
                if self.__click:
                    __game = Game(self, self.__gui)
                    __game.gameLoop()
            self.__click = False

            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    pygame.quit()
                    exit()
                if event.type == pygame.MOUSEBUTTONDOWN:
                    if event.button == 1:
                        self.__click = True

            self.__gui.updateScreen()

import pygame
from GUI import GUI
from Player import Player


# starts Game Loop and calls GUI methods
class Game:

    def __init__(self, gui, menu):
        # Pygame init should be called as very first!
        pygame.init()
        self.__status = 0
        self.__wasteMeter = 50
        self.__score = 0
        self.__click = False
        self.__gui = gui
        self.__menu = menu
        self.__player = Player()

    def getStatus(self):
        return self.__status

    def getWasteMeter(self):
        return self.__wasteMeter

    def getScore(self):
        return self.__score

    def gameLoop(self):
        while True:
            self.__gui.fillScreen()

            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    pygame.quit()
                    exit()
                if event.type == pygame.KEYDOWN:
                    if event.key == pygame.K_ESCAPE:
                        self.pauseLoop()

            self.__player.draw(self.__gui.getScreen())
            self.__gui.updateScreen()

    def pauseLoop(self):
        running = True
        while running:

            mouseX, mouseY = pygame.mouse.get_pos()

            #creates and renders quit button
            buttonQuitGame = self.__gui.generateImage(890, 60, 100, 50)
            self.__gui.render((220,160,140), buttonQuitGame)

            #if mouse collides quit button and click = True -> will go back to menu loop
            if buttonQuitGame.collidepoint((mouseX, mouseY)):
                if self.__click:
                    self.__menu.menuLoop()
            self.__click = False

            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    pygame.quit()
                    exit()
                if event.type == pygame.KEYDOWN:
                    if event.key == pygame.K_ESCAPE:
                        running = False
                if event.type == pygame.MOUSEBUTTONDOWN:
                    if event.button == 1:
                        self.__click = True

            self.__gui.updateScreen()



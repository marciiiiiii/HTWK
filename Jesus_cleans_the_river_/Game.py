import pygame

import WasteMeter
from Gui import Gui
from Menu import Menu
from Player import Player
from Fish import Fish
from Bible import Bible
from Waste import Waste
from HazWaste import HazWaste
from random import randint


# @brief Main Game Class
# @detail Handles Game, Calls Screens depending on game state and handled events
class Game:
    __BUTTONWIDTH = 60
    __BUTTONHEIGHT = 20
    __PADDINGTOPQUIT = 40
    __PADDINGTOPCONTINUE = 70

    def __init__(self):
        # Create elements needed for the base game
        # @Layla Change the value of Gui Constructor to fit your screen!
        self.__gui = Gui(700)
        self.__menu = False  # ...is created once when needed
        self.__player = Player(self.__gui)

        # Starting Status (Default: "MENU")
        self.__status = "MENU"
        # Some more Attributes
        self.__wasteMeter = WasteMeter.WasteMeter(self.__gui)
        self.__score = 0
        self.__click = False
        # Timer
        self.scoreUp = pygame.USEREVENT + 1
        pygame.time.set_timer(self.scoreUp, 500)

        # Create the static graphical elements
        self.__background = self.__gui.scaleImage(pygame.image.load("images/Background.png"),
                                                  self.__gui.getDimension("width"),
                                                  self.__gui.getDimension("height"))
        self.__bridge = self.__gui.scaleImage(pygame.image.load("images/Bridge.png"),
                                              self.__gui.getDimension("width"),
                                              self.__gui.getDimension("height"))
        self.__buttonQuitGame = self.__gui.scaleImage(pygame.image.load("images/Quit_Button.png"),
                                                      self.__BUTTONWIDTH * self.__gui.getScale(),
                                                      self.__BUTTONHEIGHT * self.__gui.getScale())
        self.__buttonContinueGame = self.__gui.scaleImage(pygame.image.load("images/Continue_Button.png"),
                                                          self.__BUTTONWIDTH * self.__gui.getScale(),
                                                          self.__BUTTONHEIGHT * self.__gui.getScale())
        self.__quitRect = self.__buttonQuitGame.get_rect(topleft=(self.__gui.getDimension("width") / 4,
                                                                  self.__gui.getScale() * self.__PADDINGTOPQUIT))
        self.__continueRect = self.__buttonContinueGame.get_rect(topleft=(self.__gui.getDimension("width") / 4,
                                                                          self.__gui.getScale() * self.__PADDINGTOPCONTINUE))

        # Group which will contain items displayed on screen
        self.__itemGroup = self.createItemGroup()

    # @brief main programm loop
    # @detail Calls the loop function of the screen currently visible depending on game state and calls event listener
    def mainLoop(self):
        while True:
            if self.getStatus() == "MENU":
                if not self.__menu:
                    self.__menu = Menu(self, self.__gui)
                self.__menu.menuLoop()
            elif self.getStatus() == "INGAME":
                self.gameLoop()
            elif self.getStatus() == "PAUSE":
                self.pauseLoop()
            else:
                self.__status = "ERR"

            self.__gui.updateScreen()

            # Event Listener
            self.eventListener()

    # @brief Main EventListener
    # @detail Handles Global Events and triggers listeners of other classes
    def eventListener(self):
        # reset click state
        self.__click = False

        # get and iterate all events since last call
        events = pygame.event.get()
        for event in events:
            # Close game (default window behaviour)
            if event.type == pygame.QUIT or self.__status == "ERR":
                pygame.quit()
                exit()
            # Checks if mouse is clicked
            if event.type == pygame.MOUSEBUTTONDOWN:
                if event.button == 1:
                    self.__click = True
            # Checks if Escape is pressed
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_ESCAPE:
                    if self.__status == "PAUSE":
                        self.startGame()
                    elif self.__status == "INGAME":
                        self.pauseGame()
            # Checks if ScoreUp is triggered
            if event.type == self.scoreUp and self.__status == "INGAME":
                self.__score += 1
        # Triggers Event Listeners of other classes
        if self.__status == "MENU":
            self.__menu.eventListener(events)

    # @brief randomly adds an item to the spawn list, thereby spawning it in the game
    def verySheapSpawningAlgo(self):
        x = randint(0, 99)
        if 70 < x < 73:
            self.__itemGroup.add(
                Fish(self.__gui.getDimension("width"), self.__gui.getDimension("height"), -5, -5, self.__gui.getScale(),
                     "images/Fisch.png", self))
        if 65 < x < 67:
            self.__itemGroup.add(
                Bible(self.__gui.getDimension("width"), self.__gui.getDimension("height"), 20, 5, self.__gui.getScale(),
                      "images/Bible.png", self))
        if 45 < x < 47:
            self.__itemGroup.add(
                Waste(self.__gui.getDimension("width"), self.__gui.getDimension("height"), 5, 5, self.__gui.getScale(),
                      "images/Trash.png", self))
        if 10 < x < 12:
            self.__itemGroup.add(HazWaste(self.__gui.getDimension("width"), self.__gui.getDimension("height"), 20, 5,
                                          self.__gui.getScale(), "images/HazWaste.png", self))

    # @brief Main game loop
    # @detail displays all necessary elements on the screen while ingame
    def gameLoop(self):

        # place background image
        self.__gui.putOnScreen(self.__background, (0, 0))

        # Item spawner
        self.verySheapSpawningAlgo()
        self.__itemGroup.draw(self.__gui.getScreen())
        self.__itemGroup.update()

        # place bridge image ontop of river / items
        self.__gui.putOnScreen(self.__bridge, (0, 0))
        # place player on screen
        self.__player.draw(self.__gui.getScreen())

        # Check for Collissions
        self.pickUpManager()
        # Check Score
        self.scoreManager()
        # Check Waste
        self.wasteManager()

    def scoreManager(self):
        self.__gui.drawText('Score: ' + str(self.__score), pygame.font.SysFont(None, 20), (255, 255, 255), 20, 90)

    def wasteManager(self):
        self.__wasteMeter.update(self.__gui.getScreen())
        if self.__wasteMeter.wasteGameLoose():
            print("YOU LOSE!")
            self.stopGame()

    def pickUpManager(self):
        collisions = pygame.sprite.spritecollide(self.__player.getSprite(), self.__itemGroup, False)
        for item in collisions:
            self.__wasteMeter.changeValue(item.getScoreChange())
            item.kill()
            del item

    # @brief Displays pause menu elements
    def pauseLoop(self):
        # Fill screen with solid color
        self.__gui.fillScreen((163, 117, 55))

        # get mouse position
        mouseX, mouseY = pygame.mouse.get_pos()

        # creates and renders quit button
        self.__gui.putOnScreen(self.__buttonQuitGame,
                               (self.__gui.getDimension("width") / 4,
                                self.__gui.getScale() * self.__PADDINGTOPQUIT))
        self.__gui.putOnScreen(self.__buttonContinueGame,
                               (self.__gui.getDimension("width") / 4,
                                self.__gui.getScale() * self.__PADDINGTOPCONTINUE))

        # if mouse collides quit button and click = True -> will go back to menu loop
        if self.__quitRect.collidepoint((mouseX, mouseY)):
            if self.__click:
                self.stopGame()

        # if mouse collides continue button and click = True -> will go back to game loop
        if self.__continueRect.collidepoint((mouseX, mouseY)):
            if self.__click:
                self.startGame()

    # @brief creates sprite group for items
    # @return item sprite group
    def createItemGroup(self):
        itemGroup = pygame.sprite.Group()
        return itemGroup

    def startGame(self):
        self.__status = "INGAME"

    def pauseGame(self):
        self.__status = "PAUSE"

    def stopGame(self):
        self.__score = 0
        self.__wasteMeter.reset()
        self.__player.reset()
        self.__itemGroup = self.createItemGroup()
        self.__status = "MENU"

    def getStatus(self):
        return self.__status

    def getWasteMeter(self):
        return self.__wasteMeter

    def getScore(self):
        return self.__score

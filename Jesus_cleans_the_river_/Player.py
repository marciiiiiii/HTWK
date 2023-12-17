import pygame
from Gui import Gui


# Main Player Class
# Generates and displays the Player Rect on Screen, handles Player Input (A,D) and
# changes X coord according to the input (moves player)
# @uses pygame.Sprite

class Player(pygame.sprite.Sprite):

    # Constants for Position calculating
    RIVERPADDING = 31
    PADDINGTOP = 90
    PLAYERHEIGHT = 18
    PLAYERWIDTH = 8

    # Attributes
    __fishNetPosX = 0

    # @brief Constructor
    # @detail Saves the Playername, sets the initial position, prepares pygame stuff
    def __init__(self, gui: Gui):
        super().__init__()

        # Set Player Name
        self.__name = "Player"

        # Set starting Position
        self.__posX = gui.getDimension("width") / 2
        self.__posY = gui.getScale() * self.PADDINGTOP;

        # Set the pygame stuff
        playerHeightScaled = self.PLAYERHEIGHT * gui.getScale()
        playerWidthScaled = self.PLAYERWIDTH * gui.getScale()
        self.image = pygame.transform.scale(pygame.image.load("./images/Jesus.png").convert_alpha(), (playerWidthScaled, playerHeightScaled))
        self.rect = self.image.get_rect(midtop=(self.__posX, self.__posY))
        self.__playerGroup = pygame.sprite.GroupSingle()
        self.__playerGroup.add(self)
        self.__gui = gui

    # @brief Checks the Player Input and draws the Image on the screen
    def draw(self, screen):
        self.playerInput()
        self.__playerGroup.draw(screen)

    def getSprite(self):
        return self.__playerGroup.sprite

    # @Event Listener
    # @brief Checks for pressed keys and changes X value accordingly
    def playerInput(self):
        # Get all key states
        keys = pygame.key.get_pressed()

        # Check if "A" or "<-" is pressed
        if keys[pygame.K_a] or keys[pygame.K_LEFT]:
            if (self.rect.left + self.PLAYERWIDTH / 2) - 10 > self.RIVERPADDING * self.__gui.getScale():
                self.rect.left -= 10

        # Check if "D" or "->" is pressed
        if keys[pygame.K_d] or keys[pygame.K_RIGHT]:
            if (self.rect.right - self.PLAYERWIDTH / 2) + 10 < \
                    self.__gui.getDimension("width") - (
                    self.RIVERPADDING * self.__gui.getScale()):
                self.rect.right += 10

    # @brief Sets the Player back to starting position
    def reset(self):
        self.__posX = self.__gui.getDimension("width") / 2

    def setName(self, name):
        self.__name = name

    def getName(self):
        return self.__name



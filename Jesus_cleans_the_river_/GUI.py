import pygame
from Fish import Fish


# @brief draws on screen
# @detail stores the dimensions and creates the game window (screen). Provedes scale, dimensions, screen for other classes and updates the screen (sets the framerate).
class Gui:
    ASPECTRATIO = 122 / 144;

    # @brief Constructor
    # @param height Sets the height of the window
    def __init__(self, height):
        self.__screen = None
        self.__height = height
        self.__width = height * self.ASPECTRATIO

        # calculate display scale
        self.__scale = self.__width / 120
        self.__clock = pygame.time.Clock()
        self.__fish = pygame.sprite.Group()

        self.generateScreen()

    # @brief generates the screen where the game content gets displayed (will be called only once)
    # @return void
    def generateScreen(self):
        self.__screen = pygame.display.set_mode((self.__width, self.__height))

    # @brief generates background for the menu
    # @return void
    def fillScreen(self, color):
        self.__screen.fill(color)

    # @brief scales images
    # @param image path to image
    # @param x size x-axis
    # @param y size y-axis
    # @return void
    def scaleImage(self, image, x, y):
        return pygame.transform.scale(image, (x, y))

    # @brief displays content at given position
    # @param image path to image 
    # @param pos position (x and y) where the image has to go on screen
    # @return void
    def putOnScreen(self, image, pos):
        self.__screen.blit(image, pos)

    # @brief draws text on the screen
    # @param text String you want to display
    # @param font font type
    # @param color font color
    # @param x coordinate x-axis
    # @param y coordinate y-axis
    # @return void
    def drawText(self, text, font, color, x, y):
        textObj = font.render(text, 1, color)
        textRect = textObj.get_rect()
        textRect.topleft = (x, y)
        self.__screen.blit(textObj, textRect)

    # @brief genenerates a rectangle (can be deleted and occurances can be replaced with return statement)
    # @return void
    def generateImage(self, left, top, width, height):
        return pygame.Rect(left, top, width, height)

    # @brief generates a rectangle in a certain color (can be deleted and occurances can be replaced with code in method)
    # @return void
    def render(self, color, object):
        pygame.draw.rect(self.__screen, color, object)

    # @brief updates the screen in a set rate of frames per second
    # @return void
    def updateScreen(self):
        pygame.display.update()
        self.__clock.tick(60)

    # @brief For accessing the screen to draw things of other classes (e.g. Player)
    # @return void
    def getScreen(self):
        return self.__screen

    # @brief returns dimenstions of screen 
    # @param dType specifies if the return value will be "width" or height"
    # @return width OR height of the screen in pixels (int)
    def getDimension(self, dType):
        if dType == "width":
            return self.__width
        else:
            return self.__height

    # @brief returns the scale with which original dimensions have to be multiplied to display them in the games dimensions
    # @return scale factor
    def getScale(self):
        return self.__scale

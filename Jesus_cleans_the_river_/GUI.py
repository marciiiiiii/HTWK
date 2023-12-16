import pygame
from Fish import Fish

#draws on screen
class GUI:

    def __init__(self, width, height):
        self.__height = width
        self.__width = height
        self.__clock = pygame.time.Clock()
        self.__fish = pygame.sprite.Group()

    #generates the screen where the game content gets displayed (will only get called once)
    def generateScreen(self):
        self.__screen = pygame.display.set_mode((self.__height, self.__width))
    
    #will generate background later on
    def fillScreen(self):
        self.__screen.fill(0x5f80a1)
    
    #draws text on the screen 
    def drawText(self, text, font, color,x, y):
        textObj = font.render(text, 1, color)
        textRect = textObj.get_rect()
        textRect.topleft = (x, y)
        self.__screen.blit(textObj, textRect)
    
    #genenerates a rectangle (has to create an image later on)
    def generateImage(self, left, top, width, height):
        return pygame.Rect(left, top, width, height)
    
    def render(self, color, object):
        pygame.draw.rect(self.__screen, color, object)

    #updates the screen in a set rate of frames per second
    def updateScreen(self):
        pygame.display.update()
        self.__clock.tick(60)
    
    def makeFish(self): # needs some parameters
        self.__fish.add(Fish(200, 300, -3, -3, 20, 30))

    def drawFish(self):
        self.__fish.draw(self.__screen)

    # For accessing the screen to draw things of other classes (e.g. Player)
    def getScreen(self):
        return self.__screen


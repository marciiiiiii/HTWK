import pygame
import math

from Gui import Gui

#@brief implements the Menu which will be shown before the game starts
#@detail the user will see the Menu screen at very first. Then it is necessary to put in the player name
#to press the "start game" button and start the game. Furthermore if the player decides to quit the game 
#or if the game is over, the menu screen will be shown aswell.
class Menu:
    # Constants for Position calculating
    __BUTTONWIDTH = 60
    __BUTTONHEIGHT = 20
    __PADDINGTOPINPUT = 40
    __PADDINGTOPSTART = 70
    __PADDINGLEFTTITLE = 7
    __PADDINGTOPTITLE = 15
    __FONTSIZETITLE = 6

    #Menu constructor
    def __init__(self, game, gui: Gui):
        self.__click = False
        self.__isInputBoxActive = False
        self.__colorInputActive = pygame.Color((122, 88, 42))
        self.__colorInputInactive = pygame.Color((89, 65, 31))
        self.__currentColorInput = self.__colorInputInactive
        self.__text = ''
        self.__fontSizeInput = math.floor((self.__BUTTONHEIGHT * gui.getScale())/1.8)
        self.__fontSizeTitle = math.floor((self.__FONTSIZETITLE * gui.getScale()))
        self.__buttonStartGame = gui.scaleImage( pygame.image.load("images/Start_Button.png"),
                                                        self.__BUTTONWIDTH * gui.getScale(),
                                                        self.__BUTTONHEIGHT * gui.getScale())
        self.__inputBox = gui.generateImage(gui.getDimension("width") / 4, 
                                            gui.getScale() * self.__PADDINGTOPINPUT, 
                                            self.__BUTTONWIDTH * gui.getScale(), 
                                            self.__BUTTONHEIGHT * gui.getScale())
        self.__startRect = self.__buttonStartGame.get_rect( topleft=(gui.getDimension("width") / 4, 
                                                            gui.getScale() * self.__PADDINGTOPSTART))
        
        self.__game = game
        self.__gui = gui
        self.__gui.generateScreen()

    # @biref will start and maintain the loop which displays the menu
    # @detail renders the menu components on the screen and checks if an event occures.
    #  possible events are:
    #    1.mouse pointer coordinates collide with start button or input box while mouse button click is true
    #       inputBox: the box color changes so it appeares highlighted
    #       startGameButton: creates object of type game, passes the menu and the gui object and starts game loop 
    #    2.keydown event while inputBox is highlighted
    #       will create a string out of the pressed keyes and displays is
    #@return void
    def menuLoop(self):
        # fill screen with solid color
        self.__gui.fillScreen((163, 117, 55))

        # create and place main title
        self.__gui.drawText('Jesus Cleans The River', pygame.font.Font("fonts/joystix_monospace.ttf", self.__fontSizeTitle), (255, 255, 255), self.__PADDINGLEFTTITLE * self.__gui.getScale(), self.__PADDINGTOPTITLE * self.__gui.getScale())

        # get mouse positions
        mouseX, mouseY = pygame.mouse.get_pos()

        self.__gui.render(self.__currentColorInput, self.__inputBox)
        self.__gui.putOnScreen( self.__buttonStartGame, 
                                (self.__gui.getDimension("width") / 4, 
                                self.__gui.getScale() * self.__PADDINGTOPSTART))


        # if mouse collides Button and click = True new Game will be created and started
        if self.__startRect.collidepoint((mouseX, mouseY)):

            if self.__click and self.__text is not '':
                self.__game.startGame()

        # if mouse collides inputBox and click = True, inputBox color changes
        if self.__inputBox.collidepoint((mouseX, mouseY)):
            if self.__click:
                self.__isInputBoxActive = not self.__isInputBoxActive
        else:
            if self.__click:
                self.__isInputBoxActive = False

        self.__currentColorInput = self.__colorInputActive if self.__isInputBoxActive else self.__colorInputInactive
        self.__gui.drawText(self.__text, pygame.font.Font("fonts/joystix_monospace.ttf", self.__fontSizeInput), (200,200,200), self.__inputBox.x + (self.__BUTTONWIDTH*self.__gui.getScale())/20, self.__inputBox.y + (self.__BUTTONHEIGHT*self.__gui.getScale())/8)

    # @brief Event Listeners needed for menu
    def eventListener(self, events):
        self.__click = False
        for event in events:
            if event.type == pygame.MOUSEBUTTONDOWN:
                if event.button == 1:
                    self.__click = True
            if event.type == pygame.KEYDOWN:
                if self.__isInputBoxActive:
                    if event.key == pygame.K_RETURN:
                        self.__text = ''
                    elif event.key == pygame.K_BACKSPACE:
                        self.__text = self.__text[:-1]
                    else:
                        self.__text += event.unicode

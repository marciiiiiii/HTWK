import pygame
from Game import Game
from Menu import Menu
from GUI import GUI

# Pygame init should be called as very first!
pygame.init()

#initialize GUI

#inizialize menu
__menu = Menu(GUI(1000, 500))
__menu.menuLoop()

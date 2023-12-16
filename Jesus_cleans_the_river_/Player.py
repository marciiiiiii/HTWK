import pygame


class Player(pygame.sprite.Sprite):
    __width = 100
    __height = 100
    __fishNetPosX = 0

    def __init__(self):
        super().__init__()

        self.__name = "Player"
        # self.image = pygame.image.load(imagePath).convert_alpha()
        self.image = pygame.Surface((self.__width, self.__height))  # just for testing purposis
        self.rect = self.image.get_rect(midtop=(200, 300))
        self.image.fill("Blue")  # just for testing purposis

        self.__playerGroup = pygame.sprite.GroupSingle()
        self.__playerGroup.add(self)

    def playerInput(self):
        keys = pygame.key.get_pressed()
        if keys[pygame.K_a] or keys[pygame.K_LEFT]:
            if self.rect.left - 10 > 0:
                self.rect.left -= 10

        if keys[pygame.K_d] or keys[pygame.K_RIGHT]:
            if self.rect.right + 10 < 1000:  # TODO Global Variable for Screen Dimensions
                self.rect.right += 10

    def setName(self, name):
        self.__name = name

    def getName(self):
        return self.__name

    def draw(self, screen):
        self.playerInput()
        self.__playerGroup.draw(screen)

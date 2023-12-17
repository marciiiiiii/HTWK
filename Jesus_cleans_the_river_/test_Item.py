from Item import Item
import pygame
import unittest
from random import randint, choice
import math

#@brief generates faulty integers
#@detail    -a random integer of the maximum and minimum resolution of integers in python is chosen
#           -chosen integer is checken, weather it is a CORRECT RESULT
#           -if so, FATAL ERROR: RE RUN TESTS will be printed
#           -in this case re-run $python test_Item.py
#           -if chosen integer is supposedly faulty, it will be returned as intended error for test functions in TestItem class
#@param *exclude: a length-undefined list of integers may be inserted, to exclude correct integers
#@return ch: ch is not element of *exclude
def errorGen(*exclude):
    ch =  randint(-2147483648, 2147483647)
    #ch =  randint(0, 1) FOR TESTING PURPOSES OF errorGen()
    evaluate = True
    for x in exclude:
        if ch == x:
            evaluate = False
    if evaluate:
        return ch
    else: 
        print("FATAL ERROR: RE RUN TESTS")

#brief: tests all functionionality of module "Item.py", Test with "$ python test_Item.py", see Item.py for original functions
class TestItem(unittest.TestCase):
    pygame.init()
    pygame.display.set_mode((800, 960))

    #@brief tests getter of scoreChange
    def test_getScoreChange(self):
        #errorGen(0,1) FOR TESTING PURPOSES OF errorGen()
        self.itemsObj = Item(800, 960, 6, 20, 6, "images/Bible.png")
        self.assertEqual(self.itemsObj.getScoreChange(), 6)
        for x in range(0,100):
            self.assertNotEqual(self.itemsObj.getScoreChange(), errorGen(6))
    #@brief tests getter of trashmeterChange
    def test_getTrashmeterChange(self):
        self.itemsObj = Item(800, 960, 6, 20, 6, "images/Bible.png")
        self.assertEqual(self.itemsObj.getTrashmeterChange(), 20)
        for x in range(0,100):
            self.assertNotEqual(self.itemsObj.getTrashmeterChange(),  errorGen(20))

    #@brief asserts, weather the built-in get_height() and get_width() functions return the correct image dimensions, as this is needed later
    def test_widthAndHeightTest(self):
        self.itemsObj = Item(800, 960, 6, 20, 6, "images/Bible.png")
        self.assertEqual(self.itemsObj.image.get_width(), 36)
        self.assertEqual(self.itemsObj.image.get_height(), 42)
        for x in range(0,100):
            self.assertNotEqual(self.itemsObj.image.get_width(), errorGen(36, 42))

    #brief asserts, weather the image gets the right scaled sizes back or not
    def test_scaleTimesImage(self):
        self.itemsObj = Item(800, 960, 6, 20, 6, "images/Bible.png")
        self.assertEqual(self.itemsObj.scaleTimesImgage(True), 216)
        self.assertEqual(self.itemsObj.scaleTimesImgage(False), 252)
        for x in range(0,100):
            self.assertNotEqual(self.itemsObj.scaleTimesImgage(False), errorGen(251, 216))

    #brief asserts, if the movement summand of 7 will be applied consistantly
    def test_movement(self):
        self.itemsObj = Item(800, 960, 6, 20, 6, "images/Bible.png")
        posYinit = self.itemsObj.rect.y
        for x in range(0,100):
            posYinit += 7
            self.itemsObj.movement()
            self.assertEqual(self.itemsObj.rect.y, posYinit)
        for x in range(0,100):
            posYinit += 7
            self.itemsObj.movement()
            self.assertNotEqual(self.itemsObj.rect.y, posYinit + errorGen(7))
    
    #brief assert if sprit object is deleted by destroy(), if rect is or passed a value of  height+100
    def test_destroy(self):
        itemGroup = pygame.sprite.Group()
        self.itemsObj = Item(800, 960, 6, 20, 6, "images/Bible.png")
        itemGroup.add(self.itemsObj)
        self.itemsObj.rect.y = 3000
        self.itemsObj.destroy()
        self.assertFalse(itemGroup.has(self.itemsObj))
        
        itemGroup = pygame.sprite.Group()
        self.itemsObj = Item(800, 960, 6, 20, 6, "images/Bible.png")
        itemGroup.add(self.itemsObj)
        self.itemsObj.rect.y = 10
        self.itemsObj.destroy()
        self.assertTrue(itemGroup.has(self.itemsObj))

    #@brief tests for item start position to be in bound of river
    #@detail 100 times it will be evaluated, if item in spawned within (186 + image width/2) and (614 - image width/2)
    def test_startPosition(self):
        self.itemsObj = Item(800, 960, 6, 20, 6, "images/Bible.png")
        for x in range(0,100):
            self.assertTrue(math.floor(31 * 6 + self.itemsObj.image.get_width() / 2) <=
                                         self.itemsObj.startPosition() <=
                                         math.ceil(800 - 31 * 6 - self.itemsObj.image.get_width() / 2) )

#@brief starts tests
if __name__ == '__main__':
    unittest.main()
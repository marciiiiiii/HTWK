from Item import Item

#@brief subclass of Item, see Item.py for all documentation
class Bible(Item):

    #@brief calls base class constructor
    #@param *args: as descriped in Item.py, passed as argument list
    def __init__(self, *args):
        super().__init__(*args)
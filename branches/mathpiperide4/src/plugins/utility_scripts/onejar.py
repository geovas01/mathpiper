import os.path
import zipfile

"""
This script is used to unzip all of the .jar files in ./lib into ./out.
It is used to place multiple .jar libraries into one library.

"""


# os.path.walk can be used to traverse directories recursively
# to apply changes to a whole tree of files.
def callback( arg, dirname, fnames ):
#    sum = 0
    for file in fnames:
        print file
        zf = zipfile.ZipFile("./lib/" + file,"r")
        zf.extractall("./out/")


#        sum += os.path.getsize(file)
#    arg.append(sum)

    

arglist = []
os.path.walk("./lib",callback,arglist)

#sum = 0
#for value in arglist:
#    sum += value
#
#print "Size of directory:",sum

import struct
import hashlib
import os
import sys
 
hashSize = 900001
fileName = "data/201801_BolsaFamilia.csv"
fileAComparar = "data/201901_BolsaFamilia.csv"
indexName = "data/bolsa-hash.dat"
indexFormat = "14sLL"
keyColumnIndex = 5

buffer = 50000
 
indexStruct = struct.Struct(indexFormat)
  
def h(key):
    return int(hashlib.sha1(key.encode('utf-8')).hexdigest(),16)%hashSize
 

f2 = open(fileAComparar,"rb")
lines = f2.readlines(buffer)
linesAComparar = 1 #ignorar header

while (linesAComparar < len(lines)-1):
    ln = str(lines[linesAComparar]).split(';')
    nis = ln[keyColumnIndex] # o keycolumnIndex da linha atual, no caso o nis atual
    # nis = sys.argv[1]
         
    f = open(fileName,"rb")
    fi = open(indexName,"r+b")

    p = h(nis)
    offset = p*indexStruct.size
    i = 1
     

    while True:
        fi.seek(offset, os.SEEK_SET)
        indexRecord = indexStruct.unpack(fi.read(indexStruct.size))
        compareIndex = indexRecord[0].decode('UTF-8')[0:11] #pega nis

        if compareIndex == nis:
            f.seek(indexRecord[1], os.SEEK_SET)            
            record = ln #linha do nis
            print (ln[6]) #nome
            print (i)
            break
        offset = indexRecord[2]
        if offset == 0:
            break
        print (i)
        i +=1
    linesAComparar +=1
fi.close()
import struct
import hashlib
import os
import sys
 
hashSize = 900001
fileName = "data/201901_BolsaFamilia.csv"
fileAComparar = "data/201902_BolsaFamiliaT.csv"
indexName = "data/bolsa-hash.dat"
indexFormat = "14sLL"
keyColumnIndex = 5

buffer = 50000
 
indexStruct = struct.Struct(indexFormat)
  
def h(key):
    global hashSize
    return int(hashlib.sha1(key.encode('utf-8')).hexdigest(),16)%hashSize
 
f = open(fileName,"rb")

f2 = open(fileAComparar,"rb")
lines = f2.readlines(buffer)
linesAComparar = 0

while (linesAComparar < len(lines)-1):
    nis = str(lines[linesAComparar]).split(';')[keyColumnIndex] # o keycolumnIndex da linha atual, no caso o nis atual
    # nis = sys.argv[1]
    
    fi = open(indexName,"r+b")
    p = h(nis)
    offset = p*indexStruct.size
    i = 1

    while True:
        fi.seek(offset)
        indexRecord = indexStruct.unpack(fi.read(indexStruct.size))

        line = f.readline()
        if indexRecord[0] == nis:
            f.seek(indexRecord[1])
            record = str(line).split(';')
            print (record[4]) #nome
            print (i)
            break
        offset = indexRecord[2]
        print(offset)
        if offset == 0:
            break
        i += 1
    linesAComparar +=1
fi.close()
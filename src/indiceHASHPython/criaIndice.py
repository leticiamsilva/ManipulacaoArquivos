import struct
import hashlib
import os
 
hashSize = 900001
fileName = "data/201901_BolsaFamilia.csv"
indexName = "data/bolsa-hash.dat"
# dataFormat = "72s72s72s72s2s8s2s"
indexFormat = "14sLL"
keyColumnIndex = 5
 
# dataStruct = struct.Struct(dataFormat)
indexStruct = struct.Struct(indexFormat)

buffer = 50000
 
def h(key):
    global hashSize
    return int(hashlib.sha1(key.encode('utf-8')).hexdigest(),16)%hashSize
    # Unicode-objects must be encoded before hashing


fi = open(indexName,"wb")

vazio = indexStruct.pack(b'',0,0)
for i in range(0,hashSize):
    fi.write(vazio)
fileIndexSize = fi.tell()
fi.close()
 
f = open(fileName,"rb")
fi = open(indexName,"r+b")
 
fi.seek(0,os.SEEK_END)
fileIndexSize = fi.tell()
print ("IndexFileSize", fileIndexSize)
 
actualLine = 1

lines = f.readlines(buffer)
line = lines.append(actualLine)
# while True:
while (actualLine < len(lines)-1):
    line = lines[actualLine]
    if line == "": # EOF:
       break

    record = str(line).split(';') 
    p = h(record[keyColumnIndex]) 
    po = record[keyColumnIndex] #para debug somente
   # pi = h(record[keyColumnIndex])
    fi.seek(p*indexStruct.size)
    indexRecord = indexStruct.unpack(fi.read(indexStruct.size))  # pegar Nis
    fi.seek(p*indexStruct.size)

    if indexRecord[0] == indexStruct.unpack(indexStruct.pack(b'',0,0))[0]:
        fi.write(indexStruct.pack(record[keyColumnIndex].encode(), p, 0))
    else:
            nextPointer = indexRecord[2]
            fi.write(indexStruct.pack(indexRecord[0], indexRecord[1],fileIndexSize))
            fi.seek(0,2) # os.SEEK_END Fim do arquivo
            fi.write(indexStruct.pack(record[keyColumnIndex], p, nextPointer))
            fileIndexSize = fi.tell()

     # recordNumber += 1
    actualLine += 1
    p += len(line) + 1
   
f.close()
fi.close()
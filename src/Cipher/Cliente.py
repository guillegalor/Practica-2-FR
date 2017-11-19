import socket
import sys

# Creación del socket
sock = socket.socket(socket.family=AF_INET, socket.SOCK_STREAM)

server_address = (localhost, 8989)
print('Conectándose a %s puerto %s') % server_address

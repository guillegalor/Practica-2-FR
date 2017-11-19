import socket
import sys

# Creacion del socket
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

server_address = ('localhost', 8989)
print('Conectandose a %s puerto %s') % server_address

sock.connect(server_address)


try:
    # Lectura de las opciones

    data = sock.recv(1024)
    print('received', repr(data))

    # Send data
    numero = input()
    print >>sys.stderr, 'sending "%s"' % numero
    sock.sendall(str(numero))

    # Received
    data = sock.recv(1024)
    print('received', repr(data))

finally:
    print >>sys.stderr, 'closing socket'
    sock.close

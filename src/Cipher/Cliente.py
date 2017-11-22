import socket
import sys

# Creacion del socket
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

server_address = ('localhost', 8989)
print('Conectandose a %s puerto %s' % server_address)

sock.connect(server_address)


try:
    # Lectura de las opciones
    data = sock.recv(512)
    print(data.decode())

    # Send data
    option = input()
    sock.sendall(str(unichr(option)))

    # Respuesta del servidor / Si descomentas esta linea se ve el error
    while True:
        datas = sock.recv(1024)
        if datas:
            break

    if data == '1':
        print('Opcion correcta')
    else:
        print('Opcion incorrecta')

finally:
    print('Cerrando el socket')
    sock.close

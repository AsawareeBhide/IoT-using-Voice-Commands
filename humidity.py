import serial
ser = serial.Serial('/dev/ttyACM0', 9600)
while 1:
    temp = str(ser.readline())
    humidity = str(ser.readline())
    print ("Temperature: " +temp)
    print("\nHumidity: " +humidity)

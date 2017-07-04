import serial
import RPi.GPIO as GPIO
import time

ser = serial.Serial('/dev/ttyACM0', 9600)

GPIO.setmode (GPIO.BCM)
GPIO.setwarnings (False)
GPIO.setup (18, GPIO.OUT)
GPIO.setup (15, GPIO.OUT)
GPIO.setup (14, GPIO.OUT)

#ser.write('1')

while True:
    
    room_status = str(ser.readline())
    #print(room_status)
    if 'z' in room_status:
        GPIO.output(18, GPIO.HIGH)
        GPIO.output(15, GPIO.HIGH)
        GPIO.output(14, GPIO.HIGH)
        print ('Lights have been turned on!')
        time.sleep(3)
        GPIO.output(18, GPIO.LOW)
        GPIO.output(15, GPIO.LOW)
        GPIO.output(14, GPIO.LOW)
        print ('Lights have been turned off!')
        


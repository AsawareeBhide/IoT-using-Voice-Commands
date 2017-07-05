#!/usr/bin/env python

import os
import paho.mqtt.client as mqtt
import RPi.GPIO as GPIO

def on_connect(client, userdata, rc, a):
    print ("Connected with rc: " + str(rc))
    client.subscribe("topic")

def on_message(client, userdata, msg):
    #print ("Topic: "+ msg.topic+"\nMessage: "+str(msg.payload))
    
    if msg.payload.decode() == 'orange light on':
        print ('Orange led is ON!\n')
        GPIO.output(15,GPIO.HIGH)
        #os.system("espeak \"Orange light is on\"")
        
    elif msg.payload.decode() == 'orange light off':
        print ('Orange led is OFF!\n')
        GPIO.output(15,GPIO.LOW)
        #os.system("espeak \"Orange light is off\"")
        
    elif msg.payload.decode() == 'red light on':
        print ('Red led is ON!\n')
        GPIO.output(14,GPIO.HIGH)
        #os.system("espeak \"Red light is on\"")
        
    elif msg.payload.decode() == 'red light off':
        print ('Red led is OFF!\n')
        GPIO.output(14,GPIO.LOW)                                          
        #os.system("espeak \"Red light is off\"")

    elif msg.payload.decode() == 'blue light on':
        print ('Blue led is ON!\n')
        GPIO.output(18,GPIO.HIGH)
        #os.system("espeak \"Blue light is on\"")

    elif msg.payload.decode() == 'blue light off':
        print ('Blue led is OFF!\n')
        GPIO.output(18,GPIO.LOW)
        #os.system("espeak \"Blue light is off\"")

client = mqtt.Client()
client.connect("localhost",1883,60)
#client.connect("test.mosquitto.org", 1883, 60)

client.on_connect = on_connect
client.on_message = on_message

GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)
GPIO.setup(18,GPIO.OUT)
GPIO.setup(14,GPIO.OUT)
GPIO.setup(15,GPIO.OUT)
client.loop_forever()

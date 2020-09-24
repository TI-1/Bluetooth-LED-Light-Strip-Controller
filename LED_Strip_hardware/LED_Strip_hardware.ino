#include <FastLED.h>


#define NUM_LEDS 150
#define DATA_PIN 7
#define FRAMES_PER_SECOND  120

// Define the array of leds
CRGB leds[NUM_LEDS];

char data[8] ;
byte r = 255;
byte g = 255;
byte b = 255;
byte brightness = 128;
int speed_values[6] = {80, 50, 100, 125, 525, 13};
uint8_t data_received;

void setup() {
  Serial.begin (9600);
  LEDS.addLeds<WS2812B, DATA_PIN, GRB>(leds, NUM_LEDS).setCorrection(TypicalLEDStrip);
}

void loop() {

  Bluetooth_check();
  Bluetooth_input_conversion();
  Function_Select();

}

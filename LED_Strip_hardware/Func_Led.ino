void input_check() {
  if (Serial.available()) {
    return;
  }
}

void Flash(byte RED, byte GREEN, byte BLUE, int SpeedDelay) {
  int interval = SpeedDelay;
  unsigned long currentMillis = millis();
  static long previousMillis;
  static int LED_STATE;
  if (currentMillis - previousMillis >= interval) {
    previousMillis = currentMillis;
    if (LED_STATE) {
      setAll(RED, GREEN, BLUE);
      LED_STATE = 0;
    } else {
      setAll(0, 0, 0);
      LED_STATE = 1;
    }

  }

}

void SnowSparkle(byte red, byte green, byte blue, int SparkleDelay, int SpeedDelay) {
  setAll(red, green, blue);

  int Pixel = random(NUM_LEDS);
  setPixel(Pixel, 0xff, 0xff, 0xff);
  showStrip();
  FastLED.delay(SparkleDelay);
  setPixel(Pixel, red, green, blue);
  showStrip();
  FastLED.delay(SpeedDelay);
}


void meteorRain(byte red, byte green, byte blue, byte meteorSize, byte meteorTrailDecay, boolean meteorRandomDecay, int SpeedDelay) {
  int interval = SpeedDelay;
  unsigned long currentMillis = millis();
  static long previousMillis;
  if (currentMillis - previousMillis >= interval){
    previousMillis = currentMillis;

    setAll(0, 0, 0);
  
    for (int i = 0; i < NUM_LEDS + NUM_LEDS; i++) {
  
  
      // fade brightness all LEDs one step
      for (int j = 0; j < NUM_LEDS; j++) {
        if ( (!meteorRandomDecay) || (random(10) > 5) ) {
          fadeToBlack(j, meteorTrailDecay );
        }
      }
  
      // draw meteor
      for (uint8_t j = 0; j < meteorSize; j++) {
        if ( ( i - j < NUM_LEDS) && (i - j >= 0) ) {
          setPixel(i - j, red, green, blue);
        }
      }
  
      showStrip();
    }
  }
}

// used by meteorrain
void fadeToBlack(int ledNo, byte fadeValue) {
  // FastLED
  leds[ledNo].fadeToBlackBy( fadeValue );

}

void rainbowCycle3(int SpeedDelay) {
  static uint8_t gHue = 0;
  EVERY_N_MILLISECONDS(SpeedDelay) {
    gHue++;
  }
  // FastLED's built-in rainbow generator
  fill_rainbow( leds, NUM_LEDS, gHue, 7);
  showStrip();
  FastLED.delay(1000 / FRAMES_PER_SECOND);
}

void sinelon(int SpeedDelay)
{
  // a colored dot sweeping back and forth, with fading trails
  //EVERY_N_MILLISECONDS( 100 ) { gHue++; }
  fadeToBlackBy( leds, NUM_LEDS, 20);
  int pos = beatsin16( SpeedDelay, 0, NUM_LEDS - 1 );
  leds[pos].setRGB(r, g, b);
  showStrip();;
}


void colorWipe2(byte red, byte green, byte blue, int SpeedDelay) {
  int interval = SpeedDelay;
  unsigned long currentMillis = millis();
  static long previousMillis;
  static uint8_t i = 0;
  static uint8_t j = 0;
  static uint8_t forward = 1;
  if (forward == 1) {
    setPixel(i, red, green, blue);
    showStrip();

    if (i >= 149) {
      forward = 0;
      i = 0;

    }
  } else if (forward == 0) {
    setPixel(i, 0, 0, 0);
    showStrip();

    if (i >= 149) {
      forward = 1;
      i = 0;
    }

  }
  if (currentMillis - previousMillis >= interval) {
    previousMillis = currentMillis;
    i++;
  }
}

void RGBLoop2() {

  uint8_t R = beatsin8( 5, 0, 255);
  uint8_t G = beatsin8(5, 0, 255, 0, 170 );
  uint8_t B = beatsin8(5, 0, 255, 0, 85);

  setAll(R, G, B);
}
// ***************************************
// ** FastLed/NeoPixel Common Functions **
// ***************************************

// Apply LED color changes
void showStrip() {
  // FastLED
  input_check();
  FastLED.setBrightness(brightness);
  FastLED.show();
}

// Set a LED color (not yet visible)
void setPixel(int Pixel, byte red, byte green, byte blue) {
  // FastLED
  leds[Pixel].setRGB(red, green, blue);
}

// Set all LEDs to a given color and apply it (visible)
void setAll(byte red, byte green, byte blue) {
  fill_solid(leds, NUM_LEDS, CRGB(red, green, blue));
  showStrip();
}

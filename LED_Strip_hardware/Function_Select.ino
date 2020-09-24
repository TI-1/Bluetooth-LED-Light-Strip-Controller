void Function_Select() {
  static uint8_t FLAG;
  byte f = data[1];
  if (data[0] == 'F') {
    switch (f) {
      case 0: { //OFF
          FLAG = 20;

          break;
        }

      case 1: { //flash
          if (FLAG == 1) {
            FLAG = 0;
          }
          else {
            FLAG = 1;
            //return;
          }
          break;
        }


      case 2: { //fade
          if (FLAG == 2) {
            FLAG = 0;
          }
          else {
            FLAG = 2;
            //return;
          }
          break;
        }

      case 3: { // strobe
          if (FLAG == 3) {
            FLAG = 0;
          }
          else {
            FLAG = 3;
            //return;
          }
          break;
        }

      case 4: { //cylon
          if (FLAG == 4) {
            FLAG = 0;
          }
          else {
            FLAG = 4;
            //return;
          }
          break;
        }

      case 5: {// snowsparkle
          if (FLAG == 5) {
            FLAG = 0;
          }
          else {
            FLAG = 5;
            //return;
          }
          break;
        }

      case 6: {//meteorrain
          if (FLAG == 6) {
            FLAG = 0;
          }
          else {
            FLAG = 6;
            //return;
          }
          break;
        }

      case 7: {// rainbow cycle
          if (FLAG == 7) {
            FLAG = 0;
          }
          else {
            FLAG = 7;
            //return;
          }
          break;
        }

      case 8: { // red
          if (FLAG == 8) {
            FLAG = 0;
          }
          else {
            FLAG = 8;
            //return;
          }
          break;
        }

      case 9: { // colourwipe
          if (FLAG == 9) {
            FLAG = 0;
          }
          else {
            FLAG = 9;
            //return;
          }
          break;
        }

      case 'X':  { // green
          if (FLAG == 10) {
            FLAG = 0;
          }
          else {
            FLAG = 10;
            //return;
          }
          break;

        }

      case 'Z': { // blue
          if (FLAG == 11) {
            FLAG = 0;
          }
          else {
            FLAG = 11;
            //return;
          }
          break;

        }


    }
  }
  switch (FLAG) {

    case 0: {
        setAll(r, g, b); //default colour selector
        break;
      }

    case 1: {
        Flash(r, g, b, speed_values[4]);// flash routine
        break;

      }

    case 2: {
        RGBLoop2();
        break;
      }

    case 3: {

        r = 255;
        g = 255;
        b = 255;
        setAll(r, g, b);
        FLAG = 0;
        break;
      }

    case 4: {
        sinelon(speed_values[5]);
        break;
      }

    case 5: {
        SnowSparkle(16, 16, 16, 20, random(100, 1000));
        break;
      }

    case 6: {
        meteorRain(r, g, b, 10, 64, true, speed_values[1]);
        break;
      }

    case 7: {
        rainbowCycle3(speed_values[1]);
        break;
      }

    case 8: {
        r = 255;
        g = 0;
        b = 0;
        setAll(r, g, b);
        FLAG = 0;
        break;
      }

    case 9: {
        colorWipe2(r, g, b, speed_values[1]);
        break;
      }

    case 10: {
        r = 0;
        g = 255;
        b = 0;
        setAll(r, g, b);
        FLAG = 0;
        break;
      }

    case 11: {
        r = 0;
        g = 0;
        b = 255;
        setAll(r, g, b);
        FLAG = 0;
        break;
      }

    case 20: {
        r = 0;
        g = 0;
        b = 0;
        setAll(r, g, b);
        FLAG = 0;
        break;
      }

  }
  memset(data, '0', sizeof(data));
}

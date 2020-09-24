void Bluetooth_check() {

  if (Serial.available()) {
    delay(10);
    memset(data, '\0', sizeof(data));
    Serial.readBytesUntil('#', data, 12);
    data_received = 1;
  }
  else {
    data_received = 0;
  }

}


void Bluetooth_input_conversion() {

  if (data_received) {

    switch (data[0]) {
      case '*': {
          byte b = data[1];
          Serial.print(b);
          brightness = map(b, 0, 100, 0, 255);
          Serial.print(brightness);
          break;
        }

      case 'S': {
          byte Speed = data[1];
          speed_values[0] = map(Speed, 0, 100, 150, 10);
          speed_values[1] = map(Speed, 0, 100, 100, 0);
          speed_values[2] = map(Speed, 0, 100, 200, 0);
          speed_values[3] = map(Speed, 0, 100, 200, 50);
          speed_values[4] = map(Speed, 0, 100, 1000, 50);
          speed_values[5] = map(Speed, 0, 100, 5, 20);
          break;
        }
      case 'R': {
          r = data[1];
          break;
        }
      case 'G': {
          g = data[1];
          break;
        }
      case 'B': {
          b = data[1];
          break;
        }
    }
  }
}

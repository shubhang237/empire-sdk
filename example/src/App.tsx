import * as React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { initSDK } from 'react-native-empire-sdk';

export default function App() {
  React.useEffect(() => {
    initSDK();
  }, []);

  return (
    <View style={styles.container}>
      <Text>Result: 2</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});

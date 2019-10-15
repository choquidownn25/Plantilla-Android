package com.example.plantilla.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";
    /**
     * Se llama si se actualiza el token InstanceID. Esto puede ocurrir si la seguridad de
     * la ficha anterior había sido comprometida. Tenga en cuenta que esto se llama cuando el token InstanceID
     * se genera inicialmente, así que aquí es donde recuperarías el token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Obtenga el token InstanceID actualizado.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    /**
     * Token persistente a servidores de terceros.
     *
     * Modifique este método para asociar el token InstanceID FCM del usuario con cualquier cuenta del lado del servidor
     * mantenido por su aplicación.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
    }
}

<?php

require_once __DIR__ . '/vendor/autoload.php';

use Google\Auth\Credentials\ServiceAccountCredentials;

function getAccessToken() {

    $scopes = [
        "https://www.googleapis.com/auth/firebase.messaging"
    ];

    $credentials = new ServiceAccountCredentials(
        $scopes,
        __DIR__ . '/firebase-key.json'
    );

    $token = $credentials->fetchAuthToken();

    return $token['access_token'];
}

function sendNotification($token, $title, $body) {

    try {

        $accessToken = getAccessToken();

        $projectId = "ruangsehat-6249d";

        $url =
            "https://fcm.googleapis.com/v1/projects/"
            . $projectId .
            "/messages:send";

        $message = [

            "message" => [

                "token" => $token,

                "notification" => [

                    "title" => $title,

                    "body" => $body
                ]
            ]
        ];

        $headers = [

            "Authorization: Bearer " . $accessToken,

            "Content-Type: application/json"
        ];

        $ch = curl_init();

        curl_setopt($ch, CURLOPT_URL, $url);

        curl_setopt($ch, CURLOPT_POST, true);

        curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);

        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

        curl_setopt(
            $ch,
            CURLOPT_POSTFIELDS,
            json_encode($message)
        );

        $result = curl_exec($ch);

        curl_close($ch);

        return $result;

    } catch (Exception $e) {

        return $e->getMessage();
    }
}
?>
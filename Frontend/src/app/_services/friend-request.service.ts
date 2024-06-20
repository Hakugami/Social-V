import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FriendRequest } from '../_models/friend-request.model'; // Import the model

@Injectable({
    providedIn: 'root'
})
export class FriendRequestsService {
    private apiUrl = 'https://your-api-endpoint/friend-requests'; // Replace with your actual API endpoint


    // constructor(private http: HttpClient) {

    //  }

    getFriendRequests(): Observable<FriendRequest[]> {
        // return this.http.get<FriendRequest[]>(this.apiUrl);
        // For now, return some mock data
        return new Observable<FriendRequest[]>(observer => {
            observer.next([
                {
                    id: 2, name: 'John Doe', image: '../assets/images/user/05.jpg',
                    friendCount: 12
                },
                {
                    id: 3, name: 'Jane Smith', image: "../assets/images/user/05.jpg",
                    friendCount: 0
                }
            ]);
            observer.complete();
        });
    }

    // Add methods for handling other operations like confirming, deleting requests, etc.
}

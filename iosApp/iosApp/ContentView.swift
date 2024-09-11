import SwiftUI
import shared

struct ContentView: View {
    @State private var dogBreeds: [DogBreed] = []
    let repository = DogRepository(apiService: ApiService())

    var body: some View {
        List(dogBreeds, id: \.name) { breed in
            Text(breed.name)
        }
        .onAppear {
            fetchDogBreeds()
        }
    }

    func fetchDogBreeds() {
        repository.getDogBreeds { breeds, error in
            if let breeds = breeds {
                self.dogBreeds = breeds
            } else if let error = error {
                print("Error fetching breeds: \(error)")
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

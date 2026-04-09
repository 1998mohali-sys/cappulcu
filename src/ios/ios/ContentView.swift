import SwiftUI

struct ContentView: View {
    @StateObject private var viewModel = CappulcuViewModel()
    
    var body: some View {
        ZStack {
            Color.black.ignoresSafeArea()
            
            VStack(spacing: 40) {
                // Ses dalgası
                AudioWaveView(isListening: $viewModel.isListening)
                    .frame(height: 200)
                    .padding(.top, 50)
                
                // Durum metni
                Text(viewModel.statusText)
                    .font(.title2)
                    .foregroundColor(.white)
                
                Spacer()
                
                // Mikrofon butonu
                Button(action: {
                    viewModel.toggleListening()
                }) {
                    Image(systemName: viewModel.isListening ? "stop.fill" : "mic.fill")
                        .font(.system(size: 30))
                        .frame(width: 80, height: 80)
                        .background(viewModel.isListening ? Color.red : Color.blue)
                        .foregroundColor(.white)
                        .clipShape(Circle())
                }
                .padding(.bottom, 50)
            }
        }
    }
}

#Preview {
    ContentView()
}

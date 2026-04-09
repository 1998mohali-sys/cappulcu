import SwiftUI

struct AudioWaveView: View {
    @Binding var isListening: Bool
    
    @State private var waveHeights: [CGFloat] = Array(repeating: 0.5, count: 20)
    @State private var timer: Timer?
    
    var body: some View {
        HStack(spacing: 4) {
            ForEach(0..<waveHeights.count, id: \.self) { index in
                RoundedRectangle(cornerRadius: 2)
                    .fill(Color.cyan)
                    .frame(width: 6, height: 150 * waveHeights[index])
                    .animation(.easeInOut(duration: 0.1), value: waveHeights[index])
            }
        }
        .frame(height: 200)
        .onAppear {
            if isListening {
                startAnimating()
            }
        }
        .onChange(of: isListening) { newValue in
            if newValue {
                startAnimating()
            } else {
                stopAnimating()
            }
        }
        .onDisappear {
            stopAnimating()
        }
    }
    
    private func startAnimating() {
        timer = Timer.scheduledTimer(withTimeInterval: 0.1, repeats: true) { _ in
            withAnimation(.easeInOut(duration: 0.1)) {
                for i in 0..<waveHeights.count {
                    waveHeights[i] = CGFloat.random(in: 0.2...1.0)
                }
            }
        }
    }
    
    private func stopAnimating() {
        timer?.invalidate()
        timer = nil
        withAnimation {
            for i in 0..<waveHeights.count {
                waveHeights[i] = 0.5
            }
        }
    }
}

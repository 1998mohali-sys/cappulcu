#!/bin/bash
echo "🚀 ÇAPULCU KURULUM BAŞLIYOR..."
mkdir -p models && cd models

echo "📥 Qwen2.5 7B modeli indiriliyor..."
wget -c "https://huggingface.co/bartowski/Qwen2.5-7B-Instruct-GGUF/resolve/main/Qwen2.5-7B-Instruct-Q4_K_M.gguf"

echo "📥 Türkçe ses modeli indiriliyor..."
wget -c "https://huggingface.co/rhasspy/piper-voices/resolve/v1.0.0/tr/tr_TR/cahit/medium/tr_TR-cahit-medium.onnx"
wget -c "https://huggingface.co/rhasspy/piper-voices/resolve/v1.0.0/tr/tr_TR/cahit/medium/tr_TR-cahit-medium.onnx.json"

echo "📥 Whisper modeli indiriliyor..."
wget -c "https://huggingface.co/ggerganov/whisper.cpp/resolve/main/ggml-base.bin"

echo "✅ Kurulum tamamlandı!"

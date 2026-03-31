from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import os
from dotenv import load_dotenv
from rag_engine import process_question

# 加载环境变量
load_dotenv()

app = FastAPI()

class QuestionRequest(BaseModel):
    question: str
    session_id: str

class AnswerResponse(BaseModel):
    answer: str
    image_urls: list[str]

@app.post("/api/ask", response_model=AnswerResponse)
async def ask_question(request: QuestionRequest):
    try:
        # 使用RAG引擎处理问题
        answer = process_question(request.question)
        # 提取图片URL（暂时为空）
        image_urls = []
        return AnswerResponse(
            answer=answer,
            image_urls=image_urls
        )
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)
from langchain.llms import BaseLLM
from langchain.schema import LLMResult
from typing import Any, List, Mapping, Optional
import requests
import os

class DeepSeekLLM(BaseLLM):
    api_key: str
    model_name: str = "deepseek-chat"
    base_url: str = "https://api.deepseek.com/v1/chat/completions"

    def _call(self, prompt: str, stop: Optional[List[str]] = None, **kwargs: Any) -> str:
        headers = {
            "Content-Type": "application/json",
            "Authorization": f"Bearer {self.api_key}"
        }

        payload = {
            "model": self.model_name,
            "messages": [
                {"role": "user", "content": prompt}
            ],
            "temperature": 0.7,
            "max_tokens": 1024
        }

        if stop:
            payload["stop"] = stop

        response = requests.post(self.base_url, headers=headers, json=payload)
        response.raise_for_status()
        result = response.json()
        return result["choices"][0]["message"]["content"]

    def _generate(self, prompts: List[str], stop: Optional[List[str]] = None, **kwargs: Any) -> LLMResult:
        results = []
        for prompt in prompts:
            result = self._call(prompt, stop, **kwargs)
            results.append(result)
        return LLMResult(
            generations=[[{'text': result}] for result in results],
            llm_output={}
        )

    @property
    def _identifying_params(self) -> Mapping[str, Any]:
        return {
            "model_name": self.model_name,
            "base_url": self.base_url
        }

    @property
    def _llm_type(self) -> str:
        return "deepseek"

# 创建DeepSeek LLM实例
def create_deepseek_llm():
    api_key = os.getenv("DEEPSEEK_API_KEY")
    if not api_key:
        raise ValueError("DEEPSEEK_API_KEY environment variable is not set")
    return DeepSeekLLM(api_key=api_key)
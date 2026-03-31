from langchain_community.embeddings import HuggingFaceEmbeddings
from langchain_community.vectorstores import Chroma
from langchain_community.document_loaders import TextLoader
from langchain.text_splitter import RecursiveCharacterTextSplitter
from langchain.chains import RetrievalQA
import os
from deepseek_llm import create_deepseek_llm

# 初始化嵌入模型
embeddings = HuggingFaceEmbeddings(model_name="BAAI/bge-small-zh-v1.5")

# 初始化Chroma向量库
vector_store = Chroma(
    persist_directory="./chroma_db",
    embedding_function=embeddings
)

# 加载文档
def load_documents(directory):
    documents = []
    for filename in os.listdir(directory):
        if filename.endswith(".txt"):
            loader = TextLoader(os.path.join(directory, filename), encoding="utf-8")
            documents.extend(loader.load())
    return documents

# 分割文档
def split_documents(documents):
    text_splitter = RecursiveCharacterTextSplitter(
        chunk_size=1000,
        chunk_overlap=200
    )
    return text_splitter.split_documents(documents)

# 向向量库添加文档
def add_documents_to_vector_store(documents):
    vector_store.add_documents(documents)
    # Chroma 0.4.x版本已自动处理文档持久化，无需手动调用persist()

# 初始化RAG链
def initialize_rag_chain():
    # 使用DeepSeek作为LLM
    llm = create_deepseek_llm()
    rag_chain = RetrievalQA.from_chain_type(
        llm=llm,
        chain_type="stuff",
        retriever=vector_store.as_retriever(),
        return_source_documents=True
    )
    return rag_chain

# 处理用户问题
def process_question(question):
    rag_chain = initialize_rag_chain()
    result = rag_chain.invoke(question)
    return result["result"]

# 初始化文档

def initialize_documents():
    # 加载文档
    documents = load_documents("./documents")
    # 分割文档
    split_docs = split_documents(documents)
    # 向向量库添加文档
    add_documents_to_vector_store(split_docs)
    print("文档初始化完成")

# 当模块被导入时自动初始化文档
initialize_documents()
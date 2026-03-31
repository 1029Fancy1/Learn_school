<template>
  <div class="major-library">
    <el-container>
      <el-header height="60px">
        <div class="header-content">
          <h1>上海第二工业大学AI高考志愿填报系统</h1>
          <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
            <el-menu-item index="1">首页</el-menu-item>
            <el-menu-item index="2">专业库</el-menu-item>
            <el-menu-item index="3">智能问答</el-menu-item>
            <el-menu-item index="4">个人中心</el-menu-item>
          </el-menu>
        </div>
      </el-header>
      <el-main>
        <div class="search-section">
          <el-input
            v-model="searchQuery"
            placeholder="输入专业名称或关键词"
            clearable
            style="width: 400px"
            @keyup.enter="handleSearch"
          >
            <template #append>
              <el-button type="primary" @click="handleSearch">搜索</el-button>
            </template>
          </el-input>
        </div>
        <div class="filter-section">
          <el-select v-model="majorType" placeholder="专业类别" style="width: 150px; margin-right: 10px">
            <el-option label="全部" value="all"></el-option>
            <el-option label="工学" value="engineering"></el-option>
            <el-option label="理学" value="science"></el-option>
            <el-option label="管理学" value="management"></el-option>
            <el-option label="经济学" value="economics"></el-option>
            <el-option label="文学" value="literature"></el-option>
          </el-select>
          <el-select v-model="degreeType" placeholder="学位类型" style="width: 150px">
            <el-option label="全部" value="all"></el-option>
            <el-option label="本科" value="bachelor"></el-option>
            <el-option label="专科" value="associate"></el-option>
          </el-select>
        </div>
        <div class="major-list">
          <el-card v-for="major in filteredMajors" :key="major.id" shadow="hover" @click="showMajorDetail(major)">
            <template #header>
              <div class="card-header">
                <span>{{ major.name }}</span>
                <el-tag size="small">{{ major.type }}</el-tag>
              </div>
            </template>
            <p>{{ major.description }}</p>
            <div class="card-footer">
              <span class="degree">{{ major.degree }}</span>
              <span class="employment-rate">{{ major.employmentRate }}% 就业率</span>
            </div>
          </el-card>
        </div>
        <el-pagination
          v-if="total > 0"
          :current-page="currentPage"
          :page-size="pageSize"
          :total="total"
          layout="total, prev, pager, next"
          @current-change="handleCurrentChange"
          style="margin-top: 20px; text-align: center"
        />
      </el-main>
      <el-footer height="100px">
        <div class="footer-content">
          <p>© 2026 上海第二工业大学 AI高考志愿填报系统</p>
        </div>
      </el-footer>
    </el-container>
    <el-dialog
      v-model="dialogVisible"
      :title="selectedMajor?.name"
      width="80%"
    >
      <div v-if="selectedMajor" class="major-detail">
        <el-row :gutter="20">
          <el-col :span="12">
            <h3>专业介绍</h3>
            <p>{{ selectedMajor.detailDescription }}</p>
            <h3>课程设置</h3>
            <ul>
              <li v-for="course in selectedMajor.courses" :key="course">{{ course }}</li>
            </ul>
          </el-col>
          <el-col :span="12">
            <h3>就业前景</h3>
            <p>{{ selectedMajor.employmentProspect }}</p>
            <h3>就业方向</h3>
            <ul>
              <li v-for="direction in selectedMajor.employmentDirections" :key="direction">{{ direction }}</li>
            </ul>
            <h3>薪资水平</h3>
            <p>平均起薪：{{ selectedMajor.averageSalary }} 元/月</p>
          </el-col>
        </el-row>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">关闭</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, computed } from 'vue'
import { useRouter } from 'vue-router'

interface Major {
  id: number
  name: string
  type: string
  degree: string
  description: string
  detailDescription: string
  courses: string[]
  employmentProspect: string
  employmentDirections: string[]
  employmentRate: number
  averageSalary: number
}

export default defineComponent({
  name: 'MajorLibrary',
  setup() {
    const router = useRouter()
    const activeIndex = ref('2')
    const searchQuery = ref('')
    const majorType = ref('all')
    const degreeType = ref('all')
    const currentPage = ref(1)
    const pageSize = ref(10)
    const total = ref(0)
    const dialogVisible = ref(false)
    const selectedMajor = ref<Major | null>(null)
    
    // 模拟专业数据
    const majors = ref<Major[]>([
      {
        id: 1,
        name: '计算机科学与技术',
        type: '工学',
        degree: '本科',
        description: '培养具备计算机科学与技术基本理论和应用能力的高级专门人才',
        detailDescription: '本专业培养具备计算机科学与技术基本理论、基本知识和基本技能，能在科研部门、教育单位、企业、事业、技术和行政管理部门等单位从事计算机教学、科学研究和应用的计算机科学与技术学科的高级专门人才。',
        courses: ['数据结构', '操作系统', '计算机网络', '数据库系统', '软件工程', '人工智能'],
        employmentProspect: '随着信息技术的快速发展，计算机专业毕业生的就业前景非常广阔，可在各类企事业单位从事软件开发、系统维护、网络管理等工作。',
        employmentDirections: ['软件开发工程师', '系统分析师', '网络工程师', '数据库管理员', '人工智能工程师'],
        employmentRate: 98.5,
        averageSalary: 8000
      },
      {
        id: 2,
        name: '机械工程',
        type: '工学',
        degree: '本科',
        description: '培养具备机械工程基本理论和应用能力的高级专门人才',
        detailDescription: '本专业培养具备机械工程基本理论、基本知识和基本技能，能在机械工程领域从事设计、制造、研究、开发、管理等工作的高级专门人才。',
        courses: ['理论力学', '材料力学', '机械原理', '机械设计', '液压传动', '数控技术'],
        employmentProspect: '机械工程是国民经济的基础产业，毕业生可在机械制造、汽车、航空航天、电子等行业从事设计、制造、研究等工作。',
        employmentDirections: ['机械设计师', '制造工程师', '工艺工程师', '设备管理员', '质量控制工程师'],
        employmentRate: 97.2,
        averageSalary: 7500
      },
      {
        id: 3,
        name: '工商管理',
        type: '管理学',
        degree: '本科',
        description: '培养具备工商管理基本理论和应用能力的高级专门人才',
        detailDescription: '本专业培养具备工商管理基本理论、基本知识和基本技能，能在各类企事业单位从事管理、营销、人力资源等工作的高级专门人才。',
        courses: ['管理学原理', '市场营销', '人力资源管理', '财务管理', '企业战略管理', '组织行为学'],
        employmentProspect: '工商管理专业毕业生可在各类企事业单位从事管理、营销、人力资源等工作，就业前景广阔。',
        employmentDirections: ['企业管理', '市场营销', '人力资源管理', '财务管理', '项目管理'],
        employmentRate: 96.8,
        averageSalary: 7000
      }
    ])
    
    const filteredMajors = computed(() => {
      let result = majors.value
      
      // 搜索过滤
      if (searchQuery.value) {
        result = result.filter(major => 
          major.name.includes(searchQuery.value) || 
          major.description.includes(searchQuery.value)
        )
      }
      
      // 专业类别过滤
      if (majorType.value !== 'all') {
        result = result.filter(major => major.type === majorType.value)
      }
      
      // 学位类型过滤
      if (degreeType.value !== 'all') {
        result = result.filter(major => major.degree === degreeType.value)
      }
      
      total.value = result.length
      
      // 分页
      const start = (currentPage.value - 1) * pageSize.value
      const end = start + pageSize.value
      return result.slice(start, end)
    })
    
    const handleSelect = (key: string) => {
      // 处理菜单选择
      switch (key) {
        case '1':
          router.push('/')
          break
        case '2':
          router.push('/major-library')
          break
        case '3':
          router.push('/qna')
          break
        case '4':
          // 跳转到个人中心
          break
      }
    }
    
    const handleSearch = () => {
      currentPage.value = 1
    }
    
    const handleCurrentChange = (page: number) => {
      currentPage.value = page
    }
    
    const showMajorDetail = (major: Major) => {
      selectedMajor.value = major
      dialogVisible.value = true
    }
    
    return {
      activeIndex,
      searchQuery,
      majorType,
      degreeType,
      currentPage,
      pageSize,
      total,
      dialogVisible,
      selectedMajor,
      filteredMajors,
      handleSelect,
      handleSearch,
      handleCurrentChange,
      showMajorDetail
    }
  }
})
</script>

<style scoped>
.major-library {
  width: 100%;
  min-height: 100vh;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
  padding: 0 20px;
}

.header-content h1 {
  font-size: 20px;
  margin: 0;
}

.search-section {
  margin-bottom: 20px;
  display: flex;
  justify-content: center;
}

.filter-section {
  margin-bottom: 20px;
  display: flex;
  justify-content: center;
}

.major-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 10px;
  font-size: 14px;
  color: #606266;
}

.major-detail {
  padding: 20px;
}

.major-detail h3 {
  margin-top: 0;
  margin-bottom: 10px;
}

.major-detail p {
  margin-bottom: 20px;
  line-height: 1.5;
}

.major-detail ul {
  margin-bottom: 20px;
}

.major-detail li {
  margin-bottom: 5px;
}

.footer-content {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-color: #f5f7fa;
}
</style>
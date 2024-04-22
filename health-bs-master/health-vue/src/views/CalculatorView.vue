<template>
  <div>
    <div class="container">
      <div style="padding: 10px">
        <!-- 搜索区域 -->
        <div style="margin: 10px 0">
          <el-input
            v-model="search"
            placeholder="请输入关键字"
            style="width: 200px"
            clearable
          ></el-input>
          <el-button
            type="primary"
            style="margin-left: 5px; margin-right: 10px"
            @click="init"
            >查询</el-button
          >
        </div>

        <el-table
          v-loading="loading"
          :data="tableData"
          border
          stripe
          style="width: 100%"
        >
          <el-table-column type="index" label="序号" width="50" align="center">
          </el-table-column>
          <el-table-column prop="sex" label="性别" align="center">
          </el-table-column>
          <el-table-column prop="weight" label="体重kg" align="center">
          </el-table-column>
          <el-table-column prop="height" label="身高cm" align="center">
          </el-table-column>
          <el-table-column prop="age" label="年龄（周岁）" align="center">
          </el-table-column>
          <el-table-column prop="createTime" label="计算时间" align="center">
          </el-table-column>
          <el-table-column
            prop="createName"
            label="计算用户名称"
            align="center"
          >
          </el-table-column>
          <el-table-column prop="bodyFatPercent" label="体脂率" align="center">
          </el-table-column>
        </el-table>

        <div style="margin: 10px 0; text-align: right">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[5, 10, 20]"
            :page-size="pageSize"
            layout="prev, pager, next, sizes,total"
            :total="total"
          ></el-pagination>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import moment from "moment";

export default {
  data() {
    return {
      loading: true,
      search: "",
      currentPage: 1,
      pageSize: 10,

      total: 0,
      tableData: [],
    };
  },
  methods: {
    init() {
      request
        .get("/calculator/pageList", {
          params: {
            pageNum: this.currentPage,
            pageSize: this.pageSize,
            search: this.search,
          },
        })
        .then((res) => {
          if (res.code === "0") {
            this.tableData = res.data.records;
            this.total = res.data.total;
            this.loading = false;
          } else {
            this.$message({
              type: "error",
              message: res.msg,
            });
          }
        });
    },

    handleSizeChange(val) {
      this.pageSize = val;
      this.init();
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.init();
    },
  },
  created() {
    this.init();
  },
  mounted() {
    console.log("Component mounted");
    console.log(moment);
    //  moment(this.endTime).format("YYYY-MM-DD HH:mm:ss")
  },
};
</script>

<style scoped>
.container {
  min-height: 730px;
  background-color: white;
  margin: 20px;
  padding: 50px;

  border-radius: 5px;
}
</style>

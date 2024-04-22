<template>
  <div>
    <div class="container">
      <div style="padding: 10px">
        <div style="margin: 10px 0">
          <el-input
            v-model="search"
            placeholder="请输入关键字"
            style="width: 20%"
            clearable
          ></el-input>
          <el-button type="primary" style="margin-left: 5px" @click="load"
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
          <el-table-column
            prop="userName"
            label="用户名(身份证号)"
            width="200"
            align="center"
          >
          </el-table-column>
          <el-table-column prop="nickName" label="昵称" align="center">
          </el-table-column>
          <el-table-column prop="sex" label="性别" align="center">
            <template #default="scope">
              <p v-if="scope.row.sex === '1'">男</p>
              <p v-else>女</p>
            </template>
          </el-table-column>

          <el-table-column label="操作" width="400" align="center">
            <template #default="scope">
              <router-link
                style="color: #409eff"
                :to="
                  '/healthRecords/statistic?userId=' +
                  scope.row.id +
                  '&type=doctor'
                "
              >
                <el-button size="mini" type="success" plain
                  >查看用户信息</el-button
                >
              </router-link>
            </template>
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

export default {
  data() {
    return {
      loading: true,
      dialogVisible: false,
      dialogTitle: "",
      search: "",
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData: [],
      bookList: [],
    };
  },
  methods: {
    load() {
      this.loading = true;
      request
        .get("/user/base/userPageList", {
          params: {
            pageNum: this.currentPage,
            pageSize: this.pageSize,
            search: this.search,
          },
        })
        .then((res) => {
          this.loading = false;
          this.tableData = res.data.records;
          this.total = res.data.total;
        });
    },
    handleSizeChange(pageSize) {
      // 改变当前每页的个数触发
      this.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum) {
      // 改变当前页码触发
      this.currentPage = pageNum;
      this.load();
    },
  },
  created() {
    this.load();
  },
  mounted() {
    console.log("Component mounted");
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

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
}
</style>

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
          <el-table-column
            prop="consultationContent"
            label="咨询内容"
            width="250"
            align="center"
          >
          </el-table-column>
          <el-table-column prop="doctorName" label="医生名称" align="center">
          </el-table-column>
          <el-table-column
            prop="replyContent"
            width="250"
            label="回复内容"
            align="center"
          >
            <template #default="scope">
              <p>
                {{ scope.row.replyContent ? scope.row.replyContent : "未回复" }}
              </p>
            </template>
          </el-table-column>
          <el-table-column
            prop="createTime"
            width="200"
            label="咨询时间"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="replayTime"
            width="200"
            label="回复时间"
            align="center"
          >
            <template #default="scope">
              <p>
                {{ scope.row.replayTime ? scope.row.replayTime : "未回复" }}
              </p>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="400" align="center">
            <template #default="scope">
              <el-popconfirm
                title="确定删除吗？"
                style="margin-left: 10px"
                @confirm="handleDelete(scope.row.id)"
              >
                <template #reference>
                  <el-button size="mini" type="danger">删除</el-button>
                </template>
              </el-popconfirm>
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
        .get("/ask/userPageList", {
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

    handleDelete(id) {
      request.post("/ask/remove", { id: id }).then((res) => {
        if (res.code === "0") {
          this.$message({
            type: "success",
            message: "删除成功",
          });
          this.init();
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

<template>
  <div>
    <div class="container">
      <div style="padding: 10px">
        <el-table
          v-loading="loading"
          :data="tableData"
          border
          stripe
          style="width: 100%"
        >
          <el-table-column type="index" label="序号" width="50" align="center">
          </el-table-column>
          <el-table-column prop="doctorName" label="诊断医生" align="center">
          </el-table-column>
          <el-table-column prop="createTime" label="分析时间" align="center">
          </el-table-column>
          <el-table-column label="操作" align="center">
            <template #default="scope">
              <el-button
                size="mini"
                type="info"
                @click="showDetails(scope.row.diagnosisContent)"
                >详情</el-button
              >
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
    <el-dialog
      title="分析建议详情"
      :visible.sync="showDetailsDialog"
      width="50%"
    >
      <p>{{ detailsContent }}</p>
      <div slot="footer">
        <el-button @click="closeDetailsDialog">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";
import moment from "moment";

export default {
  data() {
    return {
      loading: true,
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData: [],
      showDetailsDialog: false,
      detailsContent: "", // 用于存储分析建议内容
    };
  },
  methods: {
    init() {
      request
        .get("/doctorSuggest/userPageList", {
          params: {
            pageNum: this.currentPage,
            pageSize: this.pageSize,
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
    showDetails(content) {
      this.detailsContent = content;
      this.showDetailsDialog = true;
    },
    closeDetailsDialog() {
      this.showDetailsDialog = false;
    },
    handleDelete(id) {
      request.post("/doctorSuggest/remove", { id: id }).then((res) => {
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

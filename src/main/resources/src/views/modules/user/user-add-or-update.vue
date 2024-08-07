<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="" prop="username">
      <el-input v-model="dataForm.username" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="password">
      <el-input v-model="dataForm.password" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="email">
      <el-input v-model="dataForm.email" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="firstName">
      <el-input v-model="dataForm.firstName" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="lastName">
      <el-input v-model="dataForm.lastName" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="birthDate">
      <el-input v-model="dataForm.birthDate" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="gender">
      <el-input v-model="dataForm.gender" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="createdAt">
      <el-input v-model="dataForm.createdAt" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="updatedAt">
      <el-input v-model="dataForm.updatedAt" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="lastLogin">
      <el-input v-model="dataForm.lastLogin" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="status">
      <el-input v-model="dataForm.status" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="role">
      <el-input v-model="dataForm.role" placeholder=""></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          username: '',
          password: '',
          email: '',
          firstName: '',
          lastName: '',
          birthDate: '',
          gender: '',
          createdAt: '',
          updatedAt: '',
          lastLogin: '',
          status: '',
          role: ''
        },
        dataRule: {
          username: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          email: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          firstName: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          lastName: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          birthDate: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          gender: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          createdAt: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          updatedAt: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          lastLogin: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          role: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/user/user/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.username = data.user.username
                this.dataForm.password = data.user.password
                this.dataForm.email = data.user.email
                this.dataForm.firstName = data.user.firstName
                this.dataForm.lastName = data.user.lastName
                this.dataForm.birthDate = data.user.birthDate
                this.dataForm.gender = data.user.gender
                this.dataForm.createdAt = data.user.createdAt
                this.dataForm.updatedAt = data.user.updatedAt
                this.dataForm.lastLogin = data.user.lastLogin
                this.dataForm.status = data.user.status
                this.dataForm.role = data.user.role
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/user/user/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'username': this.dataForm.username,
                'password': this.dataForm.password,
                'email': this.dataForm.email,
                'firstName': this.dataForm.firstName,
                'lastName': this.dataForm.lastName,
                'birthDate': this.dataForm.birthDate,
                'gender': this.dataForm.gender,
                'createdAt': this.dataForm.createdAt,
                'updatedAt': this.dataForm.updatedAt,
                'lastLogin': this.dataForm.lastLogin,
                'status': this.dataForm.status,
                'role': this.dataForm.role
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>

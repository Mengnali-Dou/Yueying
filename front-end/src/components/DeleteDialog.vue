<script setup lang="ts">
import { useI18n } from "vue-i18n";
import { computed } from "vue";

// i18n
const { t } = useI18n();

const props = defineProps<{
	dialogVisible: boolean;
	deleteType: string;
	deleteName: string;
}>();

const emit = defineEmits(["update:dialogVisible", "confirmDelete"]);

const dialogVisible = computed({
	get() {
		return props.dialogVisible;
	},
	set(dialogVisible) {
		emit("update:dialogVisible", dialogVisible);
	},
});

// 确认删除
const confirmDelete = () => {
	emit("confirmDelete");
};
</script>

<template>
	<a-modal
		v-model:open="dialogVisible"
		:title="t('action.delete') + props.deleteType"
		:ok-text="t('action.confirm')"
		:cancel-text="t('action.cancel')"
		@ok="confirmDelete"
	>
		{{
			t("message.confirmThatYouWantToDelete", {
				deleteType: props.deleteType,
				deleteName: props.deleteName,
			})
		}}
	</a-modal>
</template>

<style scoped></style>

module.exports = {
  env: {
    browser: true,
    es2021: true,
  },
  extends: [
    "eslint:recommended",
    "plugin:@typescript-eslint/recommended",
    "plugin:vue/vue3-essential",
  ],
  overrides: [
    {
      env: {
        node: true,
      },
      files: [".eslintrc.{js,cjs}"],
      parserOptions: {
        sourceType: "script",
      },
    },
  ],
  parserOptions: {
    ecmaVersion: "latest",
    parser: "@typescript-eslint/parser",
    sourceType: "module",
  },
  plugins: ["@typescript-eslint", "vue"],
  rules: {
    "no-var": "error",
    "no-multiple-empty-lines": ["warn", { max: 1 }],
    "no-console": process.env.NODE_ENV === "production" ? "error" : "off",
    "no-debugger": process.env.NODE_ENV === "production" ? "error" : "off",
    "no-unexpected-multiline": "error",
    "no-useless-escape": "off",

    "@typescript-eslint/no-unused-vars": "warn",
    "@typescript-eslint/prefer-ts-expect-error": "error",
    "@typescript-eslint/no-explicit-any": "off",
    "@typescript-eslint/no-namespace": "off",
    "@typescript-eslint/semi": "off",

    "vue/multi-word-component-names": "off",
    "vue/script-setup-uses-vars": "error",
    "vue/no-mutating-props": "off",
    "vue/attribute-hyphenation": "off",
  },
};
